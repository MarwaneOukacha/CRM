import React, { useEffect, useState } from "react";
import { PencilLine, Trash, Plus } from "lucide-react";
import Modal from "react-modal";
import * as XLSX from "xlsx";
import { saveAs } from "file-saver";
import partnerService from "../services/partnerService";
import { FaChevronLeft, FaChevronRight } from "react-icons/fa";

Modal.setAppElement("#root");

const Partners = () => {
  const [partners, setPartners] = useState([]);
  const [editOpen, setEditOpen] = useState(false);
  const [addOpen, setAddOpen] = useState(false);
  const [deleteOpen, setDeleteOpen] = useState(false);
  const [selectedPartner, setSelectedPartner] = useState(null);
  const [searchName, setSearchName] = useState("");
  const [pageInfo, setPageInfo] = useState({ page: 0, size: 10, totalElements: 0 });

  const fetch = async (page = 0, size = 10, keyword = "") => {
    try {
      const criteria = {};
      if (keyword.trim()) criteria.companyName = keyword.trim();

      const response = await partnerService.searchPartners(criteria, page, size);
      setPartners(response.content || []);
      setPageInfo({
        page: response.number,
        size: response.size,
        totalElements: response.totalElements,
      });
    } catch (error) {
      console.error("Failed to fetch partners:", error);
    }
  };

  useEffect(() => {
    fetch();
  }, []);

  const exportToExcel = () => {
    const data = partners.map((partner) => ({
      ID: partner.id,
      Company: partner.companyName,
      Contact: partner.contactName,
      Email: partner.contactEmail,
      Commission: partner.commissionRate,
      Status: partner.status,
    }));

    const worksheet = XLSX.utils.json_to_sheet(data);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "Partners");
    const wbout = XLSX.write(workbook, { bookType: "xlsx", type: "array" });
    const blob = new Blob([wbout], { type: "application/octet-stream" });
    saveAs(blob, "partners.xlsx");
  };

  const openAddModal = () => {
    setSelectedPartner({
      companyName: "",
      contactName: "",
      contactEmail: "",
      commissionRate: 0,
      contractTerms: "",
      status: "ACTIVE",
    });
    setAddOpen(true);
  };

  const handleAddChange = (e) => {
    const { name, value } = e.target;
    setSelectedPartner((prev) => ({ ...prev, [name]: value }));
  };

  const saveAdd = async (e) => {
    e.preventDefault();
    try {
      await partnerService.registerPartner(selectedPartner);
      fetch();
      setAddOpen(false);
    } catch (error) {
      console.error("Failed to create partner:", error);
    }
  };

  const openEditModal = async (partner) => {
    try {
      const data = await partnerService.getPartnerById(partner.id);
      setSelectedPartner(data);
      setEditOpen(true);
    } catch (error) {
      console.error("Failed to fetch partner:", error);
    }
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setSelectedPartner((prev) => ({ ...prev, [name]: value }));
  };

  const saveEdit = async (e) => {
    e.preventDefault();
    try {
      await partnerService.updatePartner(selectedPartner.id, selectedPartner);
      fetch();
      setEditOpen(false);
    } catch (error) {
      console.error("Failed to update partner:", error);
    }
  };

  const openDeleteModal = (partner) => {
    setSelectedPartner(partner);
    setDeleteOpen(true);
  };

  const confirmDelete = async () => {
    try {
      await partnerService.deletePartner(selectedPartner.id);
      fetch();
      setDeleteOpen(false);
    } catch (error) {
      console.error("Failed to delete partner:", error);
    }
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    fetch(0, 10, searchName);
  };

  return (
    <div className="p-6 max-w-7xl mx-auto">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-semibold text-gray-900 dark:text-gray-100">Partners Management</h2>
        <div className="flex gap-4">
          <button onClick={openAddModal} className="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md flex items-center">
            <Plus className="mr-2" /> Add Partner
          </button>
          <button onClick={exportToExcel} className="px-4 py-2 bg-green-600 hover:bg-green-700 text-white rounded-md">
            Export Excel
          </button>
        </div>
      </div>

      <form onSubmit={handleSearchSubmit} className="mb-4 flex gap-2">
        <input
          type="text"
          placeholder="Search by company name..."
          value={searchName}
          onChange={(e) => setSearchName(e.target.value)}
          className="w-64 px-3 py-2 border rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-white"
        />
        <button type="submit" className="px-4 py-2 bg-green-600 hover:bg-green-700 text-white rounded-md">Search</button>
      </form>

      <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
        <thead className="bg-gray-50 dark:bg-gray-800">
          <tr>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-500">#</th>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-500">Company</th>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-500">Contact</th>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-500">Email</th>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-500">Commission</th>
            <th className="px-4 py-2 text-left text-sm font-medium text-gray-500">Status</th>
            <th className="px-4 py-2 text-right text-sm font-medium text-gray-500">Actions</th>
          </tr>
        </thead>
        <tbody className="bg-white dark:bg-gray-900 divide-y divide-gray-200 dark:divide-gray-700">
          {partners.map((partner, index) => (
            <tr key={partner.id} className="hover:bg-gray-100 dark:hover:bg-gray-800">
              <td className="px-4 py-2">{index + 1}</td>
              <td className="px-4 py-2">{partner.companyName}</td>
              <td className="px-4 py-2">{partner.contactName}</td>
              <td className="px-4 py-2">{partner.contactEmail}</td>
              <td className="px-4 py-2">{partner.commissionRate} %</td>
              <td className="px-4 py-2">
                <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                  partner.status === "ACTIVE" ? "bg-green-100 text-green-800 dark:bg-green-800 dark:text-green-200" : "bg-yellow-100 text-yellow-800 dark:bg-yellow-800 dark:text-yellow-200"
                }`}>
                  {partner.status}
                </span>
              </td>
              <td className="px-4 py-2 text-right flex gap-4 justify-end">
                <button onClick={() => openEditModal(partner)} className="text-blue-600 hover:text-blue-900">
                  <PencilLine size={20} />
                </button>
                <button onClick={() => openDeleteModal(partner)} className="text-red-600 hover:text-red-900">
                  <Trash size={20} />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="flex justify-center items-center mt-6 space-x-4">
        <button onClick={() => fetch(pageInfo.page - 1, pageInfo.size, searchName)} disabled={pageInfo.page === 0}>
          <FaChevronLeft size={20} />
        </button>
        <span className="text-gray-800 dark:text-gray-200 text-sm">
          Page {pageInfo.page + 1} of {Math.ceil(pageInfo.totalElements / pageInfo.size)}
        </span>
        <button onClick={() => fetch(pageInfo.page + 1, pageInfo.size, searchName)} disabled={(pageInfo.page + 1) * pageInfo.size >= pageInfo.totalElements}>
          <FaChevronRight size={20} />
        </button>
      </div>

      {/* Add, Edit, and Delete modals are the same as your existing Materials component */}
    </div>
  );
};

export default Partners;