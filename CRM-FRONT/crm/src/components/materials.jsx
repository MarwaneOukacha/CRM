import React, { useEffect, useState } from "react";
import { PencilLine, Trash, Plus } from "lucide-react";
import Modal from "react-modal";
import * as XLSX from "xlsx";
import { saveAs } from "file-saver";
import axiosInstance from "../utils/axiosInstance";

Modal.setAppElement("#root");

const Materials = () => {
  const [materials, setMaterials] = useState([]);
  const [editOpen, setEditOpen] = useState(false);
  const [addOpen, setAddOpen] = useState(false);
  const [deleteOpen, setDeleteOpen] = useState(false);
  const [selectedMaterial, setSelectedMaterial] = useState(null);

  const fetchMaterials = async () => {
    try {
      const response = await axiosInstance.get("/materials", {
        params: { page: 0, size: 100 },
      });
      setMaterials(response.data.content);
    } catch (error) {
      console.error("Failed to fetch materials:", error);
    }
  };

  useEffect(() => {
    fetchMaterials();
  }, []);

  const exportToExcel = () => {
    const data = materials.map(({ id, name, status }) => ({
      ID: id,
      Name: name,
      Status: status,
    }));

    const worksheet = XLSX.utils.json_to_sheet(data);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "Materials");
    const wbout = XLSX.write(workbook, { bookType: "xlsx", type: "array" });
    const blob = new Blob([wbout], { type: "application/octet-stream" });
    saveAs(blob, "materials.xlsx");
  };

  const openAddModal = () => {
    setSelectedMaterial({ name: "", status: "ACTIVE" });
    setAddOpen(true);
  };

  const handleAddChange = (e) => {
    const { name, value } = e.target;
    setSelectedMaterial((prev) => ({ ...prev, [name]: value }));
  };

  const saveAdd = async (e) => {
    e.preventDefault();
    try {
      await axiosInstance.post("/materials", selectedMaterial);
      fetchMaterials();
      setAddOpen(false);
    } catch (error) {
      console.error("Failed to create material:", error);
    }
  };

  const openEditModal = (material) => {
    setSelectedMaterial(material);
    setEditOpen(true);
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setSelectedMaterial((prev) => ({ ...prev, [name]: value }));
  };

  const saveEdit = async (e) => {
    e.preventDefault();
    try {
      await axiosInstance.put(`/materials/${selectedMaterial.id}`, selectedMaterial);
      fetchMaterials();
      setEditOpen(false);
    } catch (error) {
      console.error("Failed to update material:", error);
    }
  };

  const openDeleteModal = (material) => {
    setSelectedMaterial(material);
    setDeleteOpen(true);
  };

  const confirmDelete = async () => {
    try {
      await axiosInstance.delete(`/materials/${selectedMaterial.id}`);
      fetchMaterials();
      setDeleteOpen(false);
    } catch (error) {
      console.error("Failed to delete material:", error);
    }
  };

  return (
    <div className="p-6 max-w-7xl mx-auto">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-semibold text-gray-900 dark:text-gray-100">
          Materials Management
        </h2>
        <div className="flex gap-4">
          <button
            onClick={openAddModal}
            className="inline-flex items-center px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
            title="Add Material"
          >
            <Plus className="mr-2 h-4 w-4" />
            Add Material
          </button>
          <button
            onClick={exportToExcel}
            className="inline-flex items-center px-4 py-2 bg-green-600 hover:bg-green-700 text-white text-sm font-medium rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2"
            title="Export materials to Excel"
          >
            Export Excel
          </button>
        </div>
      </div>

      <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
        <thead className="bg-gray-50 dark:bg-gray-800">
          <tr>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              #
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Name
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Status
            </th>
            <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Actions
            </th>
          </tr>
        </thead>
        <tbody className="bg-white dark:bg-gray-900 divide-y divide-gray-200 dark:divide-gray-700">
          {materials.map((material, index) => (
            <tr key={material.id} className="hover:bg-gray-100 dark:hover:bg-gray-800">
              <td className="px-6 py-4 text-sm text-gray-900 dark:text-gray-100">{index + 1}</td>
              <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-gray-100">{material.name}</td>
              <td className="px-6 py-4 text-sm whitespace-nowrap">
                <span
                  className={`inline-flex px-2 text-xs leading-5 font-semibold rounded-full ${
                    material.status === "ACTIVE"
                      ? "bg-green-100 text-green-800 dark:bg-green-800 dark:text-green-200"
                      : "bg-yellow-100 text-yellow-800 dark:bg-yellow-800 dark:text-yellow-200"
                  }`}
                >
                  {material.status === "ACTIVE" ? "Active" : "Inactive"}
                </span>
              </td>
              <td className="px-6 py-4 text-right text-sm font-medium flex gap-4 justify-end">
                <button
                  onClick={() => openEditModal(material)}
                  className="text-blue-600 hover:text-blue-900 dark:hover:text-blue-400"
                  aria-label={`Edit ${material.name}`}
                >
                  <PencilLine size={20} />
                </button>
                <button
                  onClick={() => openDeleteModal(material)}
                  className="text-red-600 hover:text-red-900 dark:hover:text-red-400"
                  aria-label={`Delete ${material.name}`}
                >
                  <Trash size={20} />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Add Modal */}
      <Modal
        isOpen={addOpen}
        onRequestClose={() => setAddOpen(false)}
        contentLabel="Add Material"
        overlayClassName="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
        className="bg-white dark:bg-gray-900 rounded-lg shadow-xl max-w-lg w-full p-6 outline-none"
      >
        <h3 className="text-xl font-semibold mb-4 text-gray-900 dark:text-gray-100">Add Material</h3>
        {selectedMaterial && (
          <form onSubmit={saveAdd} className="space-y-4">
            <div>
              <label htmlFor="name" className="block text-sm font-medium mb-1">
                Name
              </label>
              <input
                id="name"
                name="name"
                type="text"
                value={selectedMaterial.name}
                onChange={handleAddChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
                required
              />
            </div>

            <div>
              <label htmlFor="status" className="block text-sm font-medium mb-1">
                Status
              </label>
              <select
                id="status"
                name="status"
                value={selectedMaterial.status}
                onChange={handleAddChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
              >
                <option value="ACTIVE">Active</option>
                <option value="INACTIVE">Inactive</option>
              </select>
            </div>

            <div className="flex justify-end gap-4">
              <button
                type="button"
                onClick={() => setAddOpen(false)}
                className="px-4 py-2 rounded border border-gray-300 dark:border-gray-600"
              >
                Cancel
              </button>
              <button type="submit" className="px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700">
                Add
              </button>
            </div>
          </form>
        )}
      </Modal>

      {/* Edit Modal */}
      <Modal
        isOpen={editOpen}
        onRequestClose={() => setEditOpen(false)}
        contentLabel="Edit Material"
        overlayClassName="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
        className="bg-white dark:bg-gray-900 rounded-lg shadow-xl max-w-lg w-full p-6 outline-none"
      >
        <h3 className="text-xl font-semibold mb-4 text-gray-900 dark:text-gray-100">Edit Material</h3>
        {selectedMaterial && (
          <form onSubmit={saveEdit} className="space-y-4">
            <div>
              <label htmlFor="name" className="block text-sm font-medium mb-1">
                Name
              </label>
              <input
                id="name"
                name="name"
                type="text"
                value={selectedMaterial.name}
                onChange={handleEditChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
                required
              />
            </div>

            <div>
              <label htmlFor="status" className="block text-sm font-medium mb-1">
                Status
              </label>
              <select
                id="status"
                name="status"
                value={selectedMaterial.status}
                onChange={handleEditChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
              >
                <option value="ACTIVE">Active</option>
                <option value="INACTIVE">Inactive</option>
              </select>
            </div>

            <div className="flex justify-end gap-4">
              <button type="button" onClick={() => setEditOpen(false)} className="px-4 py-2 rounded border border-gray-300 dark:border-gray-600">
                Cancel
              </button>
              <button type="submit" className="px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700">
                Save
              </button>
            </div>
          </form>
        )}
      </Modal>

      {/* Delete Modal */}
      <Modal
        isOpen={deleteOpen}
        onRequestClose={() => setDeleteOpen(false)}
        contentLabel="Delete Material"
        overlayClassName="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
        className="bg-white dark:bg-gray-900 rounded-lg shadow-xl max-w-md w-full p-6 outline-none"
      >
        <h3 className="text-xl font-semibold mb-4 text-gray-900 dark:text-gray-100">Confirm Delete</h3>
        <p className="text-gray-700 dark:text-gray-300">
          Are you sure you want to delete <span className="font-semibold">{selectedMaterial?.name}</span>?
        </p>
        <div className="flex justify-end gap-4 mt-6">
          <button onClick={() => setDeleteOpen(false)} className="px-4 py-2 rounded border border-gray-300 dark:border-gray-600">
            Cancel
          </button>
          <button onClick={confirmDelete} className="px-4 py-2 rounded bg-red-600 text-white hover:bg-red-700">
            Delete
          </button>
        </div>
      </Modal>
    </div>
  );
};

export default Materials;
