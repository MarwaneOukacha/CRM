import React, { useEffect, useState } from "react";
import { PencilLine, Trash, Plus, ChevronLeft, ChevronRight } from "lucide-react";
import Modal from "react-modal";
import * as XLSX from "xlsx";
import { saveAs } from "file-saver";
import { useNavigate } from "react-router-dom";
import productService from "../services/productService";

Modal.setAppElement("#root");

const Products = () => {
  const [products, setProducts] = useState([]);
  const [addOpen, setAddOpen] = useState(false);
  const [deleteOpen, setDeleteOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [searchName, setSearchName] = useState("");
    const [pageInfo, setPageInfo] = useState({ page: 0, size: 10, totalElements: 0 });

  // Pagination states
  const [page, setPage] = useState(0);
  const [size] = useState(10); // page size (can be adjustable)
  const [totalPages, setTotalPages] = useState(0);

  const navigate = useNavigate();

  const handleSearchChange = (e) => {
    setSearchName(e.target.value);
  };

  const fetchProducts = async (pageNumber = 0) => {
    try {
      const response = await productService.search(pageNumber, size);
      setProducts(response.data.content);
      setPage(response.data.number);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error("Failed to fetch products:", error);
    }
  };

  useEffect(() => {
    fetchProducts(page);
  }, [page]);

  const exportToExcel = () => {
    const data = products.map(
      ({
        id,
        name,
        code,
        price,
        status,
        size,
        clicks,
        favorite,
        cart,
        type,
      }) => ({
        ID: id,
        Name: name,
        Code: code,
        Price: price,
        Status: status,
        Size: size,
        Clicks: clicks,
        Favorites: favorite,
        Cart: cart,
        Type: type,
      })
    );
    const worksheet = XLSX.utils.json_to_sheet(data);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "Products");
    const wbout = XLSX.write(workbook, { bookType: "xlsx", type: "array" });
    const blob = new Blob([wbout], { type: "application/octet-stream" });
    saveAs(blob, "products.xlsx");
  };

  const openAddModal = () => {
    setSelectedProduct({
      name: "",
      code: "",
      price: 0,
      status: "FOR_SALE",
      size: null,
      clicks: 0,
      favorite: 0,
      cart: 0,
      type: "",
    });
    setAddOpen(true);
  };

  const handleAddChange = (e) => {
    const { name, value } = e.target;
    setSelectedProduct((prev) => ({ ...prev, [name]: value }));
  };

  const saveAdd = async (e) => {
    e.preventDefault();
    try {
      await productService.create(selectedProduct);
      fetchProducts(page);
      setAddOpen(false);
    } catch (error) {
      console.error("Failed to create product:", error);
    }
  };

  const openDeleteModal = (product) => {
    setSelectedProduct(product);
    setDeleteOpen(true);
  };

  const fetch = async (page = 0, size = 10, keyword = "") => {
    try {
      const criteria = {};
      if (keyword.trim()) criteria.keyword = keyword.trim();

      const pageable = { page, size };
      const response = await productService.search(criteria, pageable);
      setProducts(response.data.content);
      setPageInfo({
        page: response.data.number,
        size: response.data.size,
        totalElements: response.data.totalElements,
      });
    } catch (error) {
      console.error("Failed to fetch products:", error);
    }
  };
  const handleSearchSubmit = (e) => {
    e.preventDefault();
    fetch(0, pageInfo.size, searchName);
  };

  const confirmDelete = async () => {
    try {
      await productService.delete(selectedProduct.id);
      fetchProducts(page);
      setDeleteOpen(false);
    } catch (error) {
      console.error("Failed to delete product:", error);
    }
  };

  // Pagination handlers
  const goToPreviousPage = () => {
    if (page > 0) setPage(page - 1);
  };

  const goToNextPage = () => {
    if (page + 1 < totalPages) setPage(page + 1);
  };

  // Navigate to product detail page on edit icon click
  const openEditPage = (product) => {
    navigate(`/products/${product.id}`);
  };

  return (
    <div className="p-6 max-w-7xl mx-auto">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-semibold text-gray-900 dark:text-gray-100">
          Products Management
        </h2>
        <div className="flex gap-4">
          <button
            onClick={openAddModal}
            className="inline-flex items-center px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium rounded-md shadow-sm"
          >
            <Plus className="mr-2 h-4 w-4" /> Add Product
          </button>
          <button
            onClick={exportToExcel}
            className="inline-flex items-center px-4 py-2 bg-green-600 hover:bg-green-700 text-white text-sm font-medium rounded-md shadow-sm"
          >
            Export Excel
          </button>
        </div>
      </div>
      <form onSubmit={handleSearchSubmit} className="mb-4 flex gap-2">
        <input
          type="text"
          placeholder="Search by name..."
          value={searchName}
          onChange={handleSearchChange}
          className="w-64 px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
        />
        <button
          type="submit"
          className="px-4 py-2 bg-green-600 hover:bg-green-700 text-white rounded-md"
        >
          Search
        </button>
      </form>
      <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
        <thead className="bg-gray-50 dark:bg-gray-800">
          <tr>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
              #
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
              Name
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
              Code
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
              Price
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
              Status
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
              Size
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
              Clicks
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
              Favorites
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
              Cart
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
              Type
            </th>
            <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">
              Actions
            </th>
          </tr>
        </thead>
        <tbody className="bg-white dark:bg-gray-900 divide-y divide-gray-200 dark:divide-gray-700">
          {products.map((product, index) => (
            <tr
              key={product.id}
              className="hover:bg-gray-100 dark:hover:bg-gray-800"
            >
              <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-gray-100">
                {page * size + index + 1}
              </td>
              <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-gray-100">
                {product.name}
              </td>
              <td className="px-6 py-4 text-sm text-gray-700 dark:text-gray-300">
                {product.code}
              </td>
              <td className="px-6 py-4 text-sm text-gray-700 dark:text-gray-300">
                ${product.price?.toFixed(2) ?? "-"}
              </td>
              <td className="px-6 py-4 text-sm">
                <span
                  className={`inline-flex px-2 text-xs leading-5 font-semibold rounded-full ${
                    product.status === "FOR_SALE"
                      ? "bg-green-100 text-green-800 dark:bg-green-800 dark:text-green-200"
                      : "bg-yellow-100 text-yellow-800 dark:bg-yellow-800 dark:text-yellow-200"
                  }`}
                >
                  {product.status}
                </span>
              </td>
              <td className="px-6 py-4 text-sm text-gray-700 dark:text-gray-300">
                {product.size || "-"}
              </td>
              <td className="px-6 py-4 text-sm text-gray-700 dark:text-gray-300">
                {product.clicks ?? 0}
              </td>
              <td className="px-6 py-4 text-sm text-gray-700 dark:text-gray-300">
                {product.favorite ?? 0}
              </td>
              <td className="px-6 py-4 text-sm text-gray-700 dark:text-gray-300">
                {product.cart ?? 0}
              </td>
              <td className="px-6 py-4 text-sm text-gray-700 dark:text-gray-300">
                {product.type || "-"}
              </td>
              <td className="px-6 py-4 text-right text-sm font-medium flex gap-4 justify-end">
                <button
                  onClick={() => openEditPage(product)}
                  className="text-blue-600 hover:text-blue-900 dark:hover:text-blue-400"
                  aria-label="Edit Product"
                >
                  <PencilLine size={20} />
                </button>
                <button
                  onClick={() => openDeleteModal(product)}
                  className="text-red-600 hover:text-red-900 dark:hover:text-red-400"
                  aria-label="Delete Product"
                >
                  <Trash size={20} />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Pagination controls */}
      <div className="flex justify-between items-center mt-4">
        <button
          onClick={goToPreviousPage}
          disabled={page === 0}
          className={`inline-flex items-center px-3 py-1 rounded border ${
            page === 0
              ? "border-gray-300 text-gray-400 cursor-not-allowed"
              : "border-gray-600 text-gray-700 hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-gray-700"
          }`}
          aria-label="Previous Page"
        >
          <ChevronLeft size={18} />
          Prev
        </button>

        <span className="text-sm text-gray-700 dark:text-gray-300">
          Page {page + 1} of {totalPages}
        </span>

        <button
          onClick={goToNextPage}
          disabled={page + 1 >= totalPages}
          className={`inline-flex items-center px-3 py-1 rounded border ${
            page + 1 >= totalPages
              ? "border-gray-300 text-gray-400 cursor-not-allowed"
              : "border-gray-600 text-gray-700 hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-gray-700"
          }`}
          aria-label="Next Page"
        >
          Next
          <ChevronRight size={18} />
        </button>
      </div>

      {/* Add Modal */}
      <Modal
        isOpen={addOpen}
        onRequestClose={() => setAddOpen(false)}
        contentLabel="Add Product"
        overlayClassName="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
        className="bg-white dark:bg-gray-900 rounded-lg shadow-xl max-w-lg w-full p-6 outline-none"
      >
        <h3 className="text-xl font-semibold mb-4 text-gray-900 dark:text-gray-100">
          Add Product
        </h3>
        {selectedProduct && (
          <form onSubmit={saveAdd} className="space-y-4">
            <div>
              <label htmlFor="name" className="block text-sm font-medium mb-1">
                Name
              </label>
              <input
                id="name"
                name="name"
                type="text"
                value={selectedProduct.name}
                onChange={handleAddChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
                required
              />
            </div>

            <div>
              <label htmlFor="code" className="block text-sm font-medium mb-1">
                Code
              </label>
              <input
                id="code"
                name="code"
                type="text"
                value={selectedProduct.code}
                onChange={handleAddChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
              />
            </div>

            <div>
              <label htmlFor="price" className="block text-sm font-medium mb-1">
                Price
              </label>
              <input
                id="price"
                name="price"
                type="number"
                step="0.01"
                value={selectedProduct.price}
                onChange={handleAddChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
              />
            </div>

            <div>
              <label htmlFor="status" className="block text-sm font-medium mb-1">
                Status
              </label>
              <select
                id="status"
                name="status"
                value={selectedProduct.status}
                onChange={handleAddChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
              >
                <option value="FOR_SALE">For Sale</option>
                <option value="SOLD">Sold</option>
                <option value="INACTIVE">Inactive</option>
              </select>
            </div>

            <div>
              <label htmlFor="size" className="block text-sm font-medium mb-1">
                Size
              </label>
              <input
                id="size"
                name="size"
                type="number"
                step="0.1"
                value={selectedProduct.size || ""}
                onChange={handleAddChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
              />
            </div>

            <div>
              <label htmlFor="type" className="block text-sm font-medium mb-1">
                Type
              </label>
              <input
                id="type"
                name="type"
                type="text"
                value={selectedProduct.type}
                onChange={handleAddChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
              />
            </div>

            <div className="flex justify-end gap-4">
              <button
                type="button"
                onClick={() => setAddOpen(false)}
                className="px-4 py-2 rounded border border-gray-300 dark:border-gray-600"
              >
                Cancel
              </button>
              <button
                type="submit"
                className="px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700"
              >
                Add
              </button>
            </div>
          </form>
        )}
      </Modal>

      {/* Delete Modal */}
      <Modal
        isOpen={deleteOpen}
        onRequestClose={() => setDeleteOpen(false)}
        contentLabel="Delete Product"
        overlayClassName="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
        className="bg-white dark:bg-gray-900 rounded-lg shadow-xl max-w-md w-full p-6 outline-none"
      >
        <h3 className="text-xl font-semibold mb-4 text-gray-900 dark:text-gray-100">
          Confirm Delete
        </h3>
        <p className="text-gray-700 dark:text-gray-300">
          Are you sure you want to delete{" "}
          <span className="font-semibold">{selectedProduct?.name}</span>?
        </p>
        <div className="flex justify-end gap-4 mt-6">
          <button
            onClick={() => setDeleteOpen(false)}
            className="px-4 py-2 rounded border border-gray-300 dark:border-gray-600"
          >
            Cancel
          </button>
          <button
            onClick={confirmDelete}
            className="px-4 py-2 rounded bg-red-600 text-white hover:bg-red-700"
          >
            Delete
          </button>
        </div>
      </Modal>
    </div>
  );
};

export default Products;
