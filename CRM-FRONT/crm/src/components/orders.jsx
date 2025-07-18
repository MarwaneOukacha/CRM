import React, { useState } from "react";
import { Eye, Plus, Trash } from "lucide-react";
import OrderDetails from "./OrderDetails"; // We will create this component next
import { useNavigate } from "react-router-dom";

// New mock data based on your DTO structure
const mockOrdersDTO = [
  {
    code: "a1b2c3d4-e",
    customerCode: "CUST001",
    customerEmail: "customer1@example.com",
    type: "RENTAL",
    status: "DELIVERED",
    paymentType: "CREDIT_CARD",
    totalPrice: 1500.0,
    depositPaid: 300.0,
    penaltyFee: 0.0,
    damageFee: 0.0,
    totalDebt: 0.0,
    contractSent: true,
    filUrl: "/contracts/contract-001.pdf",
    orderItems: [
      {
        id: "oi-111",
        productCode: "PROD-A",
        quantity: 2.0,
        pricePerUnit: 750.0,
        rentalStartDate: "2025-07-15",
        rentalEndDate: "2025-08-15",
      },
    ],
    statusHistory: [
      { status: "PENDING", date: "2025-07-10T10:00:00Z" },
      { status: "CONFIRMED", date: "2025-07-11T11:00:00Z" },
      { status: "DELIVERED", date: "2025-07-15T09:30:00Z" },
    ],
  },
  {
    code: "f0e9d8c7",
    customerCode: "CUST002",
    customerEmail: "customer2@example.com",
    type: "SALE",
    status: "PENDING",
    paymentType: "BANK_TRANSFER",
    totalPrice: 450.5,
    depositPaid: 0.0,
    penaltyFee: 0.0,
    damageFee: 0.0,
    totalDebt: 450.5,
    contractSent: false,
    filUrl: null,
    orderItems: [
      {
        id: "oi-222",
        productCode: "PROD-B",
        quantity: 1.0,
        pricePerUnit: 450.5,
        rentalStartDate: null,
        rentalEndDate: null,
      },
      {
        id: "oi-333",
        productCode: "PROD-C",
        quantity: 5.0,
        pricePerUnit: 0.0, // Included as a freebie
        rentalStartDate: null,
        rentalEndDate: null,
      },
    ],
    statusHistory: [{ status: "PENDING", date: "2025-07-18T09:00:00Z" }],
  },
];


const Orders = () => {
  const [orders, setOrders] = useState(mockOrdersDTO);
  const [selectedOrder, setSelectedOrder] = useState(null); // State for the selected order
  const navigate=useNavigate();

  // Open the details view
  const viewOrderDetails = (order) => {
    setSelectedOrder(order);
  };

  // Close the details view
  const closeDetails = () => {
    setSelectedOrder(null);
  };

  // Handle deletion
  const openDeleteModal = (order) => {
    if (window.confirm(`Delete Order: ${order.id}?`)) {
      setOrders((prev) => prev.filter((o) => o.id !== order.id));
    }
  };
  
  // Conditionally render the details component or the table
  if (selectedOrder) {
    return <OrderDetails order={selectedOrder} onClose={closeDetails} />;
  }

  return (
    <div className="card">
      {/* Search form remains the same */}
      <div className="flex items-center justify-between mb-4 flex-wrap gap-2">
  <form className="flex gap-2">
    <input
      type="text"
      placeholder="Search by name..."
      className="w-64 px-3 py-2 border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-700 dark:text-gray-100"
    />
    <button
      type="submit"
      className="px-4 py-2 bg-green-600 hover:bg-green-700 text-white rounded-md"
    >
      Search
    </button>
  </form>

  <div className="flex gap-2">
    <button
    onClick={()=>navigate('/create/order')}
     className="inline-flex items-center px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium rounded-md">
      <Plus className="mr-2 h-4 w-4" /> Create order
    </button>
    <button className="inline-flex items-center px-4 py-2 bg-green-600 hover:bg-green-700 text-white text-sm font-medium rounded-md">
      Export Excel
    </button>
  </div>
</div>

      <div className="card-header">
        <p className="card-title">All Orders</p>
      </div>
      <div className="card-body p-0">
        <div className="relative h-[500px] w-full flex-shrink-0 overflow-auto rounded-none [scrollbar-width:_thin]">
          <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
            <thead className="bg-gray-50 dark:bg-gray-800">
              <tr>
                {/* Updated Table Headers */}
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Customer</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Order ID</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Type</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Total Price</th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Status</th>
                <th className="relative px-6 py-3"><span className="sr-only">Actions</span></th>
              </tr>
            </thead>
            <tbody className="bg-white dark:bg-gray-900 divide-y divide-gray-200 dark:divide-gray-700">
              {orders.map((order) => (
                <tr key={order.id} className="hover:bg-gray-100 dark:hover:bg-gray-800 transition">
                  {/* Updated Table Data */}
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-gray-100">{order.customerEmail}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">{order.code.substring(0, 8)}...</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">{order.type}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">${order.totalPrice.toFixed(2)}</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className={`inline-flex px-2 text-xs leading-5 font-semibold rounded-full ${
                        order.status === "DELIVERED" ? "bg-green-100 text-green-800 dark:bg-green-800 dark:text-green-200"
                      : order.status === "CONFIRMED" ? "bg-blue-100 text-blue-800 dark:bg-blue-800 dark:text-blue-200"
                      : order.status === "CANCELLED" ? "bg-red-100 text-red-800 dark:bg-red-800 dark:text-red-200"
                      : "bg-yellow-100 text-yellow-800 dark:bg-yellow-800 dark:text-yellow-200"
                    }`}>
                      {order.status}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium flex gap-4 justify-end">
                    {/* Updated Action Button to view details */}
                    <button
                      className="text-blue-600 hover:text-blue-900 dark:hover:text-blue-400"
                      aria-label={`View order ${order.id}`}
                      onClick={() => viewOrderDetails(order)}
                    >
                      <Eye size={20} />
                    </button>
                    
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Orders;