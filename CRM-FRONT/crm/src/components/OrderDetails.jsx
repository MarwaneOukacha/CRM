import React from 'react';
import { ArrowLeft } from 'lucide-react';

const OrderDetails = ({ order, onClose }) => {
  // Helper to format currency
  const formatCurrency = (amount) => `$${amount.toFixed(2)}`;
  const formatDate = (dateString) => new Date(dateString).toLocaleString();
  
  return (
    <div className="card">
      <div className="card-header flex justify-between items-center">
        <p className="card-title">Order Details</p>
        <button
          onClick={onClose}
          className="flex items-center gap-2 px-4 py-2 bg-gray-200 hover:bg-gray-300 dark:bg-gray-700 dark:hover:bg-gray-600 text-sm font-medium rounded-md"
        >
          <ArrowLeft size={16} />
          Back to List
        </button>
      </div>

      <div className="card-body grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Main Order Info */}
        <div className="space-y-4">
          <h3 className="text-lg font-semibold text-gray-800 dark:text-gray-200">General Information</h3>
          <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Order ID:</strong> <span className="font-mono text-sm text-gray-600 dark:text-gray-400">{order.id}</span></div>
          <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Customer:</strong> <span className="font-mono text-sm text-gray-600 dark:text-gray-400">{order.customerEmail} ({order.customerCode})</span> </div>
          <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Order Type:</strong> <span className="font-mono text-sm text-gray-600 dark:text-gray-400"> {order.type}</span></div>
          <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Status:</strong><span className="font-mono text-sm text-gray-600 dark:text-gray-400"> {order.status}</span> </div>
          <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Contract Sent:</strong><span className="font-mono text-sm text-gray-600 dark:text-gray-400"> {order.contractSent ? 'Yes' : 'No'}</span></div>
          {order.filUrl && <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Contract Link:</strong> <a href={order.filUrl} target="_blank" rel="noopener noreferrer" className="text-blue-500 hover:underline">View Document</a></div>}
        </div>

        {/* Financial Info */}
        <div className="space-y-4">
          <h3 className="text-lg font-semibold text-gray-800 dark:text-gray-200">Financial Summary</h3>
          <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Payment Type:</strong><span className="font-mono text-sm text-gray-600 dark:text-gray-400"> {order.paymentType}</span></div>
          <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Total Price:</strong><span className="font-mono text-sm text-gray-600 dark:text-gray-400"> {formatCurrency(order.totalPrice)}</span></div>
          <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Deposit Paid:</strong> <span className="font-mono text-sm text-gray-600 dark:text-gray-400">{formatCurrency(order.depositPaid)}</span></div>
          <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Penalty Fee:</strong> <span className="text-orange-500">{formatCurrency(order.penaltyFee)}</span></div>
          <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Damage Fee:</strong> <span className="text-red-500">{formatCurrency(order.damageFee)}</span></div>
          <div><strong className='whitespace-nowrap text-sm text-gray-900 dark:text-gray-100'>Total Debt:</strong> <span className="font-bold text-red-600">{formatCurrency(order.totalDebt)}</span></div>
        </div>
      </div>
      
      {/* Order Items Section */}
      <div className="card-footer mt-6">
        <h3 className="text-lg font-semibold text-gray-800 dark:text-gray-200 mb-4">Order Items</h3>
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
            <thead className="bg-gray-50 dark:bg-gray-800">
              <tr>
                <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">Product Code</th>
                <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">Quantity</th>
                <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">Price/Unit</th>
                <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">Rental Start</th>
                <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">Rental End</th>
              </tr>
            </thead>
            <tbody className="bg-white dark:bg-gray-900 divide-y divide-gray-200 dark:divide-gray-700">
              {order.orderItems.map(item => (
                <tr key={item.id}>
                  <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">{item.productCode}</td>
                  <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">{item.quantity}</td>
                  <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">{formatCurrency(item.pricePerUnit)}</td>
                  <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">{item.rentalStartDate || 'N/A'}</td>
                  <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">{item.rentalEndDate || 'N/A'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      {/* -- START: NEW STATUS HISTORY SECTION -- */}
      <div className="card-footer mt-6 border-t dark:border-gray-700 pt-6">
        <h3 className="text-lg font-semibold text-gray-800 dark:text-gray-200 mb-4">Status History 📜</h3>
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
            <thead className="bg-gray-50 dark:bg-gray-800">
              <tr>
                <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">Status</th>
                <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase">Date</th>
              </tr>
            </thead>
            <tbody className="bg-white dark:bg-gray-900 divide-y divide-gray-200 dark:divide-gray-700">
              {order.statusHistory
                .slice() // Create a shallow copy to avoid mutating the original array
                .sort((a, b) => new Date(b.date) - new Date(a.date)) // Sort by most recent date first
                .map((historyItem, index) => (
                  <tr key={index}>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">{historyItem.status}</td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">{formatDate(historyItem.date)}</td>
                  </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default OrderDetails;