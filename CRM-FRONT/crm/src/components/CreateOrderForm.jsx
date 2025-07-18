import React, { useState, useEffect } from 'react';
import { Search, PlusCircle, Trash2, FileUp, FileText } from 'lucide-react';

// --- Mock API Simulation ---
const mockProducts = {
  "JEW-A": {
    name: "Diamond Necklace",
    description: "Elegant white gold necklace with certified diamonds.",
    price: 3200.0,
    rentPrice: 250.0,
    code: "JEW-A",
    quantity: 5,
    status: "AVAILABLE",
  },
  "JEW-B": {
    name: "Gold Bracelet",
    description: "24K pure gold bracelet with intricate design.",
    price: 850.0,
    rentPrice: 80.0,
    code: "JEW-B",
    quantity: 10,
    status: "AVAILABLE",
  },
  "JEW-C": {
    name: "Pearl Earrings",
    description: "Natural freshwater pearl earrings with silver hooks.",
    price: 150.0,
    rentPrice: 15.0,
    code: "JEW-C",
    quantity: 0,
    status: "OUT_OF_STOCK",
  },
};

const fetchProductByCode = (code) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      const product = mockProducts[code.toUpperCase()];
      if (product) resolve({ success: true, data: product });
      else resolve({ success: false, message: "Product not found" });
    }, 500);
  });
};
// --- End of Mock API Simulation ---

const CreateOrderForm = () => {
  // --- STATE MANAGEMENT ---
  const [orderData, setOrderData] = useState({
    customerCode: '',
    customerEmail: '',
    type: 'SALE',
    totalPrice: 0.0,
    orderItems: [],
  });
  
  const [contractFile, setContractFile] = useState(null);
  const [productCodeInput, setProductCodeInput] = useState('');
  const [searchedProduct, setSearchedProduct] = useState(null);
  const [quantityToAdd, setQuantityToAdd] = useState(1);
  const [searchLoading, setSearchLoading] = useState(false);
  const [searchError, setSearchError] = useState('');

  // --- EFFECTS ---
  useEffect(() => {
    const total = orderData.orderItems.reduce((sum, item) => {
      const price = orderData.type === 'RENT' ? item.rentPrice : item.price;
      return sum + (price * item.quantity);
    }, 0);
    setOrderData(prev => ({ ...prev, totalPrice: total.toFixed(2) }));
  }, [orderData.orderItems, orderData.type]);

  // --- HANDLERS ---
  const handleFormChange = (e) => setOrderData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  const handleFileChange = (e) => e.target.files && setContractFile(e.target.files[0]);
  
  const handleSearchProduct = async () => {
    if (!productCodeInput) return;
    setSearchLoading(true);
    setSearchError('');
    setSearchedProduct(null);
    const response = await fetchProductByCode(productCodeInput);
    if (response.success) {
      setSearchedProduct(response.data);
    } else {
      setSearchError(response.message);
    }
    setSearchLoading(false);
  };

  const handleAddItemToOrder = () => {
    if (!searchedProduct || quantityToAdd <= 0) return;
    const newItem = { ...searchedProduct, quantity: Number(quantityToAdd) };
    setOrderData(prev => ({ ...prev, orderItems: [...prev.orderItems, newItem] }));
    setSearchedProduct(null);
    setProductCodeInput('');
    setQuantityToAdd(1);
    setSearchError('');
  };
  
  const removeOrderItem = (productCode) => {
    setOrderData(prev => ({
      ...prev,
      orderItems: prev.orderItems.filter(item => item.code !== productCode),
    }));
  };
  
  const handleGenerateContract = () => {
    console.log("Generating contract for order:", orderData);
    alert(`Generating contract for order with total: $${orderData.totalPrice}`);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const finalData = { ...orderData };
    if (contractFile) finalData.signedContract = contractFile;
    console.log("Submitting Order:", finalData);
    alert("Order data has been logged to the console.");
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white dark:bg-gray-900 shadow-md rounded-lg">
      <div className="p-4 border-b border-gray-200 dark:border-gray-700">
        <p className="text-xl font-bold text-gray-900 dark:text-gray-100">Create New Order</p>
      </div>

      <div className="p-4 md:p-6 grid grid-cols-1 md:grid-cols-2 gap-x-6 gap-y-8">
        {/* --- Left Column: Customer & Contract --- */}
        <div className="space-y-4">
            <h3 className="text-lg font-semibold text-gray-800 dark:text-gray-200">Customer & Order Details</h3>
            <div>
                <label className="block text-sm font-medium mb-1 text-gray-700 dark:text-gray-300">Customer Code</label>
                <input type="text" name="customerCode" value={orderData.customerCode} className="w-full px-3 py-2 text-sm border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-600 focus:ring-2 focus:ring-blue-500 focus:border-blue-500" required />
            </div>
            <div>
                <label className="block text-sm font-medium mb-1 text-gray-700 dark:text-gray-300">Customer Email</label>
                <input type="email" name="customerEmail" value={orderData.customerEmail} className="w-full px-3 py-2 text-sm border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-600 focus:ring-2 focus:ring-blue-500 focus:border-blue-500" required />
            </div>
            <div>
                <label className="block text-sm font-medium mb-1 text-gray-700 dark:text-gray-300">Order Type</label>
                <select name="type" value={orderData.type} onChange={handleFormChange} className="w-full px-3 py-2 text-sm border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-600 focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                    <option value="SALE">Sale</option>
                    <option value="RENT">Rent</option>
                </select>
            </div>
             <div>
                <label className="block text-sm font-medium mb-1 text-gray-700 dark:text-gray-300">Upload Signed Contract</label>
                <label htmlFor="contractUpload" className="w-full px-3 py-2 text-sm border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-600 flex items-center gap-2 cursor-pointer text-gray-500 dark:text-gray-400">
                    <FileUp size={16}/>{contractFile ? contractFile.name : 'Choose a file...'}
                </label>
                <input id="contractUpload" type="file" className="hidden" onChange={handleFileChange} />
            </div>
        </div>

        {/* --- Right Column: Product Search & Preview --- */}
        <div className="space-y-4">
            <h3 className="text-lg font-semibold text-gray-800 dark:text-gray-200">Find Product</h3>
            <div className="flex items-start gap-2">
                <input type="text" value={productCodeInput} onChange={(e) => setProductCodeInput(e.target.value)} className="w-full px-3 py-2 text-sm border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-600 focus:ring-2 focus:ring-blue-500 focus:border-blue-500" placeholder="Enter Product Code"/>
                <button type="button" onClick={handleSearchProduct} disabled={searchLoading} className="inline-flex items-center justify-center p-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 disabled:opacity-50 transition-colors">
                    {searchLoading ? <div className="w-5 h-5 border-2 border-white/50 border-t-white rounded-full animate-spin"></div> : <Search size={20} />}
                </button>
            </div>
            {searchError && <p className="text-sm text-red-500 mt-2">{searchError}</p>}
            
            {/* THIS IS THE SECTION THAT DISPLAYS THE PRODUCT INFO */}
            {searchedProduct && (
                <div className="mt-2 p-3 border rounded-md bg-gray-50 dark:bg-gray-700/50 space-y-3">
                    <h4 className="font-semibold text-gray-800 dark:text-gray-100">{searchedProduct.name}</h4>
                    <p className="text-sm text-gray-500 dark:text-gray-400">{searchedProduct.description}</p>
                    
                    <div className="text-sm space-y-1 pt-2 border-t border-gray-200 dark:border-gray-600">
                      <div><strong className="text-gray-600 dark:text-gray-300 w-24 inline-block">Code:</strong> <span className="font-mono">{searchedProduct.code}</span></div>
                      <div><strong className="text-gray-600 dark:text-gray-300 w-24 inline-block">Status:</strong> <span className={searchedProduct.status === 'AVAILABLE' ? 'text-green-600' : 'text-orange-500'}>{searchedProduct.status}</span></div>
                      <div><strong className="text-gray-600 dark:text-gray-300 w-24 inline-block">In Stock:</strong> {searchedProduct.quantity}</div>
                      <div><strong className="text-gray-600 dark:text-gray-300 w-24 inline-block">Sale Price:</strong> ${searchedProduct.price.toFixed(2)}</div>
                      <div><strong className="text-gray-600 dark:text-gray-300 w-24 inline-block">Rent Price:</strong> ${searchedProduct.rentPrice.toFixed(2)}</div>
                    </div>

                    <div className="flex items-center gap-4 pt-3 border-t border-gray-200 dark:border-gray-600">
                        <label className="text-sm font-medium">Qty:</label>
                        <input type="number" value={quantityToAdd} onChange={(e) => setQuantityToAdd(e.target.value)} min="1" max={searchedProduct.quantity} className="w-20 px-3 py-1.5 text-sm border border-gray-300 rounded-md dark:bg-gray-800 dark:border-gray-600"/>
                        <button type="button" onClick={handleAddItemToOrder} className="inline-flex items-center justify-center gap-1.5 px-3 py-1.5 text-sm bg-green-600 text-white rounded-md hover:bg-green-700 ml-auto">
                            <PlusCircle size={16} /> Add
                        </button>
                    </div>
                </div>
            )}
        </div>
      </div>
      
      {/* --- Order Items Table --- */}
      <div className="px-4 pb-4 md:px-6 md:pb-6 mt-6">
        <h3 className="text-lg font-semibold text-gray-800 dark:text-gray-200 mb-4">Order Summary</h3>
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
            {/* Table Head */}
            <thead className="bg-gray-50 dark:bg-gray-800">
              <tr>
                <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Product</th>
                <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Qty</th>
                <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Unit Price</th>
                <th className="px-4 py-2 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Total</th>
                <th className="px-4 py-2 text-right text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            {/* Table Body */}
            <tbody className="bg-white dark:bg-gray-900 divide-y divide-gray-200 dark:divide-gray-700">
              {orderData.orderItems.length > 0 ? orderData.orderItems.map(item => {
                const unitPrice = orderData.type === 'RENT' ? item.rentPrice : item.price;
                return (
                  <tr key={item.code}>
                    <td className="px-4 py-3 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-gray-100">{item.name}</td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">{item.quantity}</td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">${unitPrice.toFixed(2)}</td>
                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-500 dark:text-gray-300">${(unitPrice * item.quantity).toFixed(2)}</td>
                    <td className="px-4 py-3 whitespace-nowrap text-right text-sm font-medium">
                        <button type="button" onClick={() => removeOrderItem(item.code)} className="text-red-600 hover:text-red-900 dark:hover:text-red-400"><Trash2 size={18} /></button>
                    </td>
                  </tr>
                )
              }) : (
                <tr><td colSpan="5" className="text-center py-8 text-gray-500 dark:text-gray-400">No products added yet.</td></tr>
              )}
            </tbody>
            {/* Table Foot */}
            <tfoot className="border-t-2 border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-800">
                <tr>
                    <td colSpan="3" className="px-4 py-3 text-right text-sm font-bold text-gray-800 dark:text-gray-200 uppercase">Grand Total</td>
                    <td colSpan="2" className="px-4 py-3 text-left text-lg font-bold text-gray-900 dark:text-gray-100">${orderData.totalPrice}</td>
                </tr>
            </tfoot>
          </table>
        </div>
      </div>
      
      <div className="px-4 pb-4 md:px-6 md:pb-6 mt-6 flex justify-end items-center gap-4">
        <button type="button" onClick={handleGenerateContract} className="flex items-center justify-center gap-2 px-4 py-2 bg-gray-200 hover:bg-gray-300 dark:bg-gray-700 dark:hover:bg-gray-600 text-sm font-medium rounded-md">
            <FileText size={16} /> Generate Contract
        </button>
        <button type="submit" className="flex items-center justify-center gap-2 px-6 py-2 bg-green-600 text-white font-semibold rounded-md hover:bg-green-700">Create Order</button>
      </div>
    </form>
  );
};

export default CreateOrderForm;