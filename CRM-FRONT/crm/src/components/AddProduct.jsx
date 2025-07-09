import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from "react-router-dom"; 
import { ArrowLeft, Save, Upload, Image as ImageIcon, PlusCircle, Trash2 } from 'lucide-react';

// --- SERVICE IMPORTS ---
// The component relies on these services being correctly configured in your project.
import productService from '../services/productService';
import categoryService from '../services/categoryService';
import caratService from '../services/caratService';
import { searchMaterials } from '../services/materialService';
import designerService from '../services/designerService';
import occasionService from '../services/occasionService';
import colorService from '../services/colorService';

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

// --- Reusable Form Components ---
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

// --- Dynamic List Component ---
const DynamicMultiSelect = ({ title, items, availableOptions, onAdd, onRemove, onChange, fieldNames }) => (
    <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-md">
        <h3 className="text-lg font-semibold mb-4 text-gray-800 dark:text-gray-100">{title}</h3>
        <div className="space-y-3">
            {(items || []).map((item, index) => (
                <div key={index} className="flex gap-3 items-center p-2 bg-gray-50 dark:bg-gray-700/50 rounded-lg">
                    <select
                        value={item[fieldNames.id] || ''}
                        onChange={(e) => onChange(index, fieldNames.id, e.target.value)}
                        className="flex-grow p-2 rounded-md border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 focus:ring-1 focus:ring-blue-500"
                    >
                        <option value="" disabled>Select {title.slice(0, -1)}...</option>
                        {availableOptions?.map(opt => <option key={opt.id} value={opt.id}>{opt.name}</option>)}
                    </select>
                    <button type="button" onClick={() => onRemove(index)} className="text-red-500 hover:text-red-700 dark:hover:text-red-400 p-2 rounded-full hover:bg-red-100 dark:hover:bg-red-900/50 transition-colors" aria-label={`Remove ${title.slice(0, -1)}`}>
                        <Trash2 size={16} />
                    </button>
                </div>
            ))}
        </div>
        <button type="button" onClick={onAdd} className="mt-4 flex items-center gap-2 text-sm text-blue-600 dark:text-blue-400 font-semibold hover:text-blue-800 dark:hover:text-blue-300 transition">
            <PlusCircle size={16} /> Add {title.slice(0, -1)}
        </button>
    </div>
);

// --- Initial State for a New Product ---
const initialProductState = {
    name: '',
    description: '',
    price: '',
    code: '',
    size: '',
    weight: '',
    status: 'FOR_SALE',
    categoryId: '',
    caratId: '',
    occasionIds: [],
    materialIds: [],
    colorIds: [],
    designerIds: [],
    media: []
};

// --- Main Component ---
const AddProduct = () => {
    const navigate = useNavigate();

    const [product, setProduct] = useState(initialProductState);
    const [options, setOptions] = useState({ categories: [], carats: [], materials: [], colors: [], designers: [], occasions: [] });
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState(null);
    const [notification, setNotification] = useState({ message: '', type: '' });
    
    // Note: No activeImage state needed for a new product initially

    // Fetch all lookup data for dropdowns on component mount
    useEffect(() => {
        const fetchLookupData = async () => {
            setLoading(true);
            setError(null);
            try {
                const [catRes, caratRes, matRes, colorRes, desRes, occRes] = await Promise.all([
                    categoryService.search(),
                    caratService.search(),
                    searchMaterials(),
                    colorService.search(),
                    designerService.search(),
                    occasionService.search(),
                ]);

                setOptions({
                    categories: catRes.data.content,
                    carats: caratRes.data.content,
                    materials: matRes.content,
                    colors: colorRes.data.content,
                    designers: desRes.content,
                    occasions: occRes.data.content,
                });
            } catch (err) {
                setError("Failed to load options for creating a product.");
                console.error("Fetch Error:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchLookupData();
    }, []);

    const showNotification = (message, type) => {
        setNotification({ message, type });
    };

    // Generic handler for simple form input changes
    const handleChange = useCallback((e) => {
        const { name, value } = e.target;
        setProduct((prev) => ({
            ...prev,
            [name]: name === "price" || name === "weight" || name === "size" ? parseFloat(value) || '' : value,
        }));
    }, []);

    // Generic handler for dynamic list changes
    const handleDynamicListChange = useCallback((listName, index, field, value) => {
        setProduct((prev) => {
            const newList = [...(prev[listName] || [])];
            const optionsKey = listName.replace('Ids', 's');
            const selectedOption = options[optionsKey]?.find(opt => opt.id === value);
            newList[index] = selectedOption ? { ...selectedOption } : { id: value };
            return { ...prev, [listName]: newList };
        });
    }, [options]);

    const addDynamicListItem = useCallback((listName) => {
        setProduct((prev) => ({
            ...prev,
            [listName]: [...(prev[listName] || []), { id: '' }],
        }));
    }, []);

    const removeDynamicListItem = useCallback((listName, index) => {
        setProduct((prev) => {
            const newList = [...(prev[listName] || [])];
            newList.splice(index, 1);
            return { ...prev, [listName]: newList };
        });
    }, []);

    // Create new product
    const addProduct = async () => {
        setSaving(true);
        try {
            const createDTO = {
              ...product,
              occasionIds: product.occasionIds.map(o => o.id),
              materialIds: product.materialIds.map(m => m.id),
              colorIds: product.colorIds.map(c => c.id),
              designerIds: product.designerIds.map(d => d.id),
            };
            console.log(createDTO);
            await productService.create(createDTO);
            showNotification('Product created successfully!', 'success');
            setTimeout(() => navigate('/products'), 1500); // Navigate to product list after success
        } catch (err) {
            showNotification('Failed to create product.', 'error');
            console.error("Create Error:", err);
        } finally {
            setSaving(false);
        }
    };
    
    // Render states
    if (loading) return <div className="flex items-center justify-center h-screen bg-gray-100 dark:bg-gray-900 text-gray-800 dark:text-gray-200"><div className="animate-spin rounded-full h-16 w-16 border-t-2 border-b-2 border-blue-500"></div></div>;
    if (error) return <div className="p-8 text-center text-red-600 dark:text-red-400 bg-red-50 dark:bg-gray-800 rounded-lg shadow-md max-w-md mx-auto mt-10">{error}</div>;

    return (
        <div className="bg-gray-100 dark:bg-gray-900 min-h-screen font-sans text-gray-800 dark:text-gray-200">
            <Notification message={notification.message} type={notification.type} onDismiss={() => setNotification({ message: '', type: '' })} />
            <div className="max-w-7xl mx-auto p-4 sm:p-6 lg:p-8">
                <header className="flex items-center justify-between mb-6">
                    <div>
                        <button onClick={() => navigate(-1)} className="flex items-center text-sm font-semibold text-blue-600 hover:text-blue-800 dark:text-blue-400 dark:hover:text-blue-300 transition-colors">
                            <ArrowLeft size={16} className="mr-2" /> Back to Products
                        </button>
                        <h1 className="text-3xl font-bold mt-1 text-gray-900 dark:text-gray-50">Add New Product</h1>
                    </div>
                    <button onClick={addProduct} disabled={saving || loading} className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-5 py-2.5 rounded-lg font-semibold shadow-md hover:shadow-lg transition-all disabled:opacity-50 disabled:cursor-not-allowed">
                        <Save size={18} /> {saving ? "Saving..." : "Save Product"}
                    </button>
                </header>

                <form onSubmit={(e) => { e.preventDefault(); addProduct(); }} className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                    {/* Left Column: Images */}
                    <div className="lg:col-span-1 space-y-6">
                         <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-md">
                            <h3 className="text-lg font-semibold mb-4 text-gray-800 dark:text-gray-100">Product Images</h3>
                            {/* Placeholder for image uploads */}
                            <div className="aspect-square w-full flex items-center justify-center bg-gray-100 dark:bg-gray-700 rounded-lg text-gray-400 dark:text-gray-500">
                                <ImageIcon size={48} />
                            </div>
                            <button type="button" className="w-full mt-4 flex items-center justify-center gap-2 text-sm text-blue-600 dark:text-blue-400 font-semibold bg-blue-50 dark:bg-blue-900/50 hover:bg-blue-100 dark:hover:bg-blue-900/75 p-2 rounded-md transition">
                                <Upload size={16} /> Upload Images
                            </button>
                         </div>
                    </div>

                    {/* Right Column: Details */}
                    <div className="lg:col-span-2 space-y-6">
                        <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-md">
                            <h3 className="text-lg font-semibold mb-4 text-gray-800 dark:text-gray-100">Core Information</h3>
                            <div className="space-y-4">
                                <FormInput id="name" name="name" label="Product Name" value={product.name} onChange={handleChange} type="text" required placeholder="e.g., Elegant Diamond Ring" />
                                <div>
                                    <label htmlFor="description" className="block text-sm font-medium text-gray-600 dark:text-gray-300 mb-1">Description</label>
                                    <textarea id="description" name="description" value={product.description} onChange={handleChange} rows={5} className="w-full p-2.5 rounded-md border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-gray-100 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition" placeholder="Describe the product..."/>
                                </div>
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                    <FormInput id="code" name="code" label="Product Code (SKU)" value={product.code} onChange={handleChange} type="text" placeholder="e.g., RNG-DMD-001" />
                                    <FormInput id="price" name="price" label="Price ($)" value={product.price} onChange={handleChange} type="number" step="0.01" min="0" placeholder="e.g., 1499.99" />
                                </div>
                            </div>
                        </div>

                        <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-md">
                            <h3 className="text-lg font-semibold mb-4 text-gray-800 dark:text-gray-100">Categorization & Specs</h3>
                            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <FormSelect id="status" name="status" label="Status" value={product.status} onChange={handleChange}>
                                    <option value="FOR_SALE">For Sale</option>
                                    <option value="OUT_OF_STOCK">Out of Stock</option>
                                    <option value="DISCONTINUED">Discontinued</option>
                                </FormSelect>
                                <FormSelect id="categoryId" name="categoryId" label="Category" value={product.categoryId} onChange={handleChange}>
                                    <option value="">Select category...</option>
                                    {options.categories?.map(opt => <option key={opt.id} value={opt.id}>{opt.name}</option>)}
                                </FormSelect>
                                <FormSelect id="caratId" name="caratId" label="Carat" value={product.caratId} onChange={handleChange}>
                                    <option value="">Select carat...</option>
                                    {options.carats?.map(opt => <option key={opt.id} value={opt.id}>{opt.name}</option>)}
                                </FormSelect>
                                <FormInput id="size" name="size" label="Size" value={product.size} onChange={handleChange} type="number" step="0.1" min="0" placeholder="e.g., 6.5" />
                                <FormInput id="weight" name="weight" label="Weight (g)" value={product.weight} onChange={handleChange} type="number" step="0.1" min="0" placeholder="e.g., 4.1" />
                            </div>
                        </div>
                        
                        <DynamicMultiSelect
                            title="Materials"
                            items={product.materialIds}
                            availableOptions={options.materials}
                            onAdd={() => addDynamicListItem('materialIds')}
                            onRemove={(index) => removeDynamicListItem('materialIds', index)}
                            onChange={(index, field, value) => handleDynamicListChange('materialIds', index, field, value)}
                            fieldNames={{ id: 'id', name: 'name' }}
                        />

                        <DynamicMultiSelect
                            title="Colors"
                            items={product.colorIds}
                            availableOptions={options.colors}
                            onAdd={() => addDynamicListItem('colorIds')}
                            onRemove={(index) => removeDynamicListItem('colorIds', index)}
                            onChange={(index, field, value) => handleDynamicListChange('colorIds', index, field, value)}
                            fieldNames={{ id: 'id', name: 'name' }}
                        />

                        <DynamicMultiSelect
                            title="Designers"
                            items={product.designerIds}
                            availableOptions={options.designers}
                            onAdd={() => addDynamicListItem('designerIds')}
                            onRemove={(index) => removeDynamicListItem('designerIds', index)}
                            onChange={(index, field, value) => handleDynamicListChange('designerIds', index, field, value)}
                            fieldNames={{ id: 'id', name: 'name' }}
                        />

                        <DynamicMultiSelect
                            title="Occasions"
                            items={product.occasionIds}
                            availableOptions={options.occasions}
                            onAdd={() => addDynamicListItem('occasionIds')}
                            onRemove={(index) => removeDynamicListItem('occasionIds', index)}
                            onChange={(index, field, value) => handleDynamicListChange('occasionIds', index, field, value)}
                            fieldNames={{ id: 'id', name: 'name' }}
                        />

                    </div>
                </form>
            </div>
        </div>
    );
};

export default AddProduct;