import React, { useState, useEffect, useCallback } from 'react';
// In a real app, you would install and import from react-router-dom
// import { useParams, useNavigate } from "react-router-dom"; 
import { ArrowLeft, Save, Upload, Image as ImageIcon, PlusCircle, Trash2 } from 'lucide-react';

// --- (Placeholder) react-router-dom hooks ---
// This is just to make the component runnable. 
// In your actual project, you'll use the real 'react-router-dom' library.
const useParams = () => ({ id: 'abc-123' });
const useNavigate = () => (path) => console.log(`Navigating to: ${path}`);

// --- (Placeholder) Product Service ---
// This would typically be in its own file, e.g., 'src/services/productService.js'
// It simulates fetching data from and sending data to an API.
const mockApiProduct = {
  id: 'abc-123',
  name: 'Elegant Diamond Solitaire Ring',
  description: 'A timeless piece featuring a brilliant-cut 0.5 carat diamond set in a 14k white gold band. Perfect for engagements, anniversaries, or as a statement of classic style. The band is smooth and polished for a comfortable fit.',
  code: 'SKU-DSR-050-WG',
  price: 1850.00,
  status: 'FOR_SALE',
  type: 'ring',
  size: 6.5,
  weight: 3.2,
  images: [
    'https://placehold.co/600x600/e2e8f0/4a5568?text=Product+Image+1'
  ],
  occasionIds: [
    { id: 'occ-1', name: 'Engagement', status: 'PUBLISHED' },
    { id: 'occ-2', name: 'Anniversary', status: 'DRAFT' },
  ]
};

const productService = {
  getById: async (id) => {
    console.log(`FETCH: Getting product with id: ${id}`);
    // Simulate API delay
    await new Promise(resolve => setTimeout(resolve, 1000));
    // In a real app, this would be an axios or fetch call
    // e.g., return axios.get(`/api/products/${id}`);
    return { data: mockApiProduct };
  },
  update: async (id, product) => {
    console.log(`UPDATE: Saving product with id: ${id}`, product);
    // Simulate API delay
    await new Promise(resolve => setTimeout(resolve, 1500));
    // In a real app, this would be an axios or fetch call
    // e.g., return axios.put(`/api/products/${id}`, product);
    return { success: true };
  }
};
// --- End of Product Service ---


// --- Constants ---
const statusOptions = [
  { value: 'FOR_SALE', label: 'For Sale' },
  { value: 'OUT_OF_STOCK', label: 'Out of Stock' },
  { value: 'DISCONTINUED', label: 'Discontinued' },
];

const typeOptions = [
  { value: 'ring', label: 'Ring' },
  { value: 'necklace', label: 'Necklace' },
  { value: 'bracelet', label: 'Bracelet' },
  { value: 'earring', label: 'Earring' },
];

const occasionStatusOptions = [
    { value: 'PUBLISHED', label: 'Published' },
    { value: 'DRAFT', label: 'Draft' },
    { value: 'ARCHIVED', label: 'Archived' },
];


// --- Helper Components ---
const Notification = ({ message, type, onDismiss }) => {
  if (!message) return null;
  const baseClasses = 'fixed top-5 right-5 p-4 rounded-lg shadow-lg text-white transition-opacity duration-300 z-50';
  const typeClasses = { success: 'bg-green-500', error: 'bg-red-500' };
  useEffect(() => {
    const timer = setTimeout(onDismiss, 3000);
    return () => clearTimeout(timer);
  }, [onDismiss]);
  return <div className={`${baseClasses} ${typeClasses[type]}`}>{message}</div>;
};


// --- Main Component ---
const ProductDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(null);
  const [notification, setNotification] = useState({ message: '', type: '' });
  const [activeImage, setActiveImage] = useState(0);

  // Fetch product data from the service on component mount
  useEffect(() => {
    const fetchProduct = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await productService.getById(id);
        setProduct(response.data);
        setActiveImage(0);
      } catch (err) {
        setError("Failed to load product details.");
        console.error("Fetch Error:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchProduct();
  }, [id]);

  const showNotification = (message, type) => {
    setNotification({ message, type });
  };

  // Generic handler for form input changes
  const handleChange = useCallback((e) => {
    const { name, value } = e.target;
    setProduct((prev) => ({
      ...prev,
      [name]: name === "price" || name === "weight" || name === "size" ? parseFloat(value) || 0 : value,
    }));
  }, []);

  // Handlers for the dynamic "Occasions" list
  const handleOccasionChange = useCallback((index, field, value) => {
    setProduct((prev) => {
      const newOccasions = [...(prev.occasionIds || [])];
      newOccasions[index] = { ...newOccasions[index], [field]: value };
      return { ...prev, occasionIds: newOccasions };
    });
  }, []);

  const addOccasion = useCallback(() => {
    setProduct((prev) => ({
      ...prev,
      occasionIds: [...(prev.occasionIds || []), { id: `new-${Date.now()}`, name: '', status: 'DRAFT' }],
    }));
  }, []);

  const removeOccasion = useCallback((index) => {
    setProduct((prev) => {
      const newOccasions = [...(prev.occasionIds || [])];
      newOccasions.splice(index, 1);
      return { ...prev, occasionIds: newOccasions };
    });
  }, []);

  // Save product changes
  const saveProduct = async () => {
    setSaving(true);
    try {
      await productService.update(id, product);
      showNotification('Product saved successfully!', 'success');
      setTimeout(() => navigate(-1), 1500); // Navigate back after showing success
    } catch (err) {
      showNotification('Failed to save product.', 'error');
      console.error("Save Error:", err);
    } finally {
      setSaving(false);
    }
  };

  // Reusable form components
  const FormInput = ({ id, label, ...props }) => (
    <div>
      <label htmlFor={id} className="block text-sm font-medium text-gray-600 dark:text-gray-300 mb-1">{label}</label>
      <input id={id} {...props} className="w-full p-2.5 rounded-md border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-gray-100 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition" />
    </div>
  );

  const FormSelect = ({ id, label, children, ...props }) => (
     <div>
        <label htmlFor={id} className="block text-sm font-medium text-gray-600 dark:text-gray-300 mb-1">{label}</label>
        <select id={id} {...props} className="w-full p-2.5 rounded-md border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-gray-100 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition">{children}</select>
     </div>
  );
  
  // Render states
  if (loading) return <div className="flex items-center justify-center h-screen bg-gray-100 dark:bg-gray-900 text-gray-800 dark:text-gray-200"><div className="animate-spin rounded-full h-16 w-16 border-t-2 border-b-2 border-blue-500"></div></div>;
  if (error) return <div className="p-8 text-center text-red-600 dark:text-red-400 bg-red-50 dark:bg-gray-800 rounded-lg shadow-md max-w-md mx-auto mt-10">{error}</div>;
  if (!product) return <div className="p-8 text-center text-gray-600 dark:text-gray-400">Product not found.</div>;

  return (
    <div className="bg-gray-100 dark:bg-gray-900 min-h-screen font-sans text-gray-800 dark:text-gray-200">
      <Notification message={notification.message} type={notification.type} onDismiss={() => setNotification({ message: '', type: '' })} />
      <div className="max-w-7xl mx-auto p-4 sm:p-6 lg:p-8">
        <header className="flex items-center justify-between mb-6">
          <div>
            <button onClick={() => navigate(-1)} className="flex items-center text-sm font-semibold text-blue-600 hover:text-blue-800 dark:text-blue-400 dark:hover:text-blue-300 transition-colors">
              <ArrowLeft size={16} className="mr-2" /> Back to Products
            </button>
            <h1 className="text-3xl font-bold mt-1 text-gray-900 dark:text-gray-50">Edit Product</h1>
          </div>
          <button onClick={saveProduct} disabled={saving} className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-5 py-2.5 rounded-lg font-semibold shadow-md hover:shadow-lg transition-all disabled:opacity-50 disabled:cursor-not-allowed">
            <Save size={18} /> {saving ? "Saving..." : "Save Changes"}
          </button>
        </header>

        <form onSubmit={(e) => { e.preventDefault(); saveProduct(); }} className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Left Column: Images */}
          <div className="lg:col-span-1 space-y-6">
             <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-md">
                <h3 className="text-lg font-semibold mb-4 text-gray-800 dark:text-gray-100">Product Images</h3>
                {product.images && product.images.length > 0 ? (
                    <div>
                        <div className="aspect-square w-full bg-gray-100 dark:bg-gray-700 rounded-lg mb-4 overflow-hidden">
                           <img src={product.images[activeImage]} alt="Product" className="w-full h-full object-cover"/>
                        </div>
                        {product.images.length > 1 && (
                            <div className="grid grid-cols-4 gap-2">
                                {product.images.map((img, index) => (
                                    <button key={index} type="button" onClick={() => setActiveImage(index)} className={`aspect-square rounded-md overflow-hidden ring-2 transition ${activeImage === index ? 'ring-blue-500' : 'ring-transparent hover:ring-blue-400'}`}>
                                        <img src={img} alt={`Thumbnail ${index + 1}`} className="w-full h-full object-cover"/>
                                    </button>
                                ))}
                            </div>
                        )}
                    </div>
                ) : (
                    <div className="aspect-square w-full flex items-center justify-center bg-gray-100 dark:bg-gray-700 rounded-lg text-gray-400 dark:text-gray-500"><ImageIcon size={48} /></div>
                )}
                <button type="button" className="w-full mt-4 flex items-center justify-center gap-2 text-sm text-blue-600 dark:text-blue-400 font-semibold bg-blue-50 dark:bg-blue-900/50 hover:bg-blue-100 dark:hover:bg-blue-900/75 p-2 rounded-md transition">
                    <Upload size={16} /> Upload New Image
                </button>
             </div>
          </div>

          {/* Right Column: Details */}
          <div className="lg:col-span-2 space-y-6">
            <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-md">
                <h3 className="text-lg font-semibold mb-4 text-gray-800 dark:text-gray-100">Core Information</h3>
                <div className="space-y-4">
                    <FormInput id="name" name="name" label="Product Name" value={product.name} onChange={handleChange} type="text" required />
                    <div>
                        <label htmlFor="description" className="block text-sm font-medium text-gray-600 dark:text-gray-300 mb-1">Description</label>
                        <textarea id="description" name="description" value={product.description || ''} onChange={handleChange} rows={5} className="w-full p-2.5 rounded-md border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-gray-100 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition" />
                    </div>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <FormInput id="code" name="code" label="Product Code (SKU)" value={product.code} onChange={handleChange} type="text" />
                        <FormInput id="price" name="price" label="Price ($)" value={product.price} onChange={handleChange} type="number" step="0.01" min="0" />
                    </div>
                </div>
            </div>

            <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-md">
                <h3 className="text-lg font-semibold mb-4 text-gray-800 dark:text-gray-100">Categorization & Specs</h3>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <FormSelect id="status" name="status" label="Status" value={product.status} onChange={handleChange}>
                        {statusOptions.map(opt => <option key={opt.value} value={opt.value}>{opt.label}</option>)}
                    </FormSelect>
                    <FormSelect id="type" name="type" label="Type" value={product.type || ''} onChange={handleChange}>
                        <option value="">Select type</option>
                        {typeOptions.map(opt => <option key={opt.value} value={opt.value}>{opt.label}</option>)}
                    </FormSelect>
                    <FormInput id="size" name="size" label="Size" value={product.size || ''} onChange={handleChange} type="number" step="0.1" min="0" />
                    <FormInput id="weight" name="weight" label="Weight (g)" value={product.weight || ''} onChange={handleChange} type="number" step="0.1" min="0" />
                </div>
            </div>

            <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-md">
                <h3 className="text-lg font-semibold mb-4 text-gray-800 dark:text-gray-100">Occasions</h3>
                <div className="space-y-3">
                    {(product.occasionIds || []).map((occ, index) => (
                        <div key={occ.id} className="flex gap-3 items-center p-2 bg-gray-50 dark:bg-gray-700/50 rounded-lg">
                            <input type="text" placeholder="Occasion Name" value={occ.name} onChange={(e) => handleOccasionChange(index, 'name', e.target.value)} className="flex-grow p-2 rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 focus:ring-1 focus:ring-blue-500" />
                            <select value={occ.status} onChange={(e) => handleOccasionChange(index, 'status', e.target.value)} className="p-2 rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 focus:ring-1 focus:ring-blue-500">
                                {occasionStatusOptions.map(opt => <option key={opt.value} value={opt.value}>{opt.label}</option>)}
                            </select>
                            <button type="button" onClick={() => removeOccasion(index)} className="text-red-500 hover:text-red-700 dark:hover:text-red-400 p-2 rounded-full hover:bg-red-100 dark:hover:bg-red-900/50 transition-colors" aria-label="Remove occasion"><Trash2 size={16} /></button>
                        </div>
                    ))}
                </div>
                <button type="button" onClick={addOccasion} className="mt-4 flex items-center gap-2 text-sm text-blue-600 dark:text-blue-400 font-semibold hover:text-blue-800 dark:hover:text-blue-300 transition"><PlusCircle size={16} /> Add Occasion</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ProductDetail;