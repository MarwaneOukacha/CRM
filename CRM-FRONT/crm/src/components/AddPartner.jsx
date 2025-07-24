import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import partnerService from '../services/partnerService';
import { toast } from 'sonner';

const AddPartner = () => {
  const navigate = useNavigate();
  const [partner, setPartner] = useState({
    name: '',
    email: '',
    phone: '',
    address: '',
    passportSeries: '',
    passportNumber: '',
    finCode: '',
    notes: '',
    receivingBankName: '',
    receivingBankCurrency: '',
    bankTIN: '',
    bankSwiftCode: '',
    bankAccountNumber: '',
    companyName: '',
  });

  const [saving, setSaving] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPartner((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const registerPartner = async () => {
    setSaving(true);
    try {
      await partnerService.registerPartner(partner);
      toast.success('Partner registered successfully!');
      setTimeout(() => navigate('/partners'), 1500);
    } catch (err) {
      console.error(err);
      toast.error('Failed to register partner.');
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="max-w-5xl mx-auto p-6 bg-white shadow rounded-md dark:bg-gray-900">
      <h2 className="text-2xl font-bold mb-4 text-gray-800 dark:text-gray-200">Add Partner</h2>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {[
          'name', 'email', 'phone', 'address', 'passportSeries', 'passportNumber',
          'finCode', 'notes', 'receivingBankName', 'receivingBankCurrency',
          'bankTIN', 'bankSwiftCode', 'bankAccountNumber', 'companyName',
        ].map((field) => (
          <div key={field}>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 capitalize">
              {field.replace(/([A-Z])/g, ' $1')}
            </label>
            <input
              type="text"
              name={field}
              value={partner[field]}
              onChange={handleChange}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 dark:border-gray-700 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm dark:bg-gray-800 dark:text-white"
            />
          </div>
        ))}
      </div>

      <div className="mt-6">
        <button
          onClick={registerPartner}
          disabled={saving}
          className={`px-6 py-2 rounded-md text-white ${
            saving ? 'bg-gray-400 cursor-not-allowed' : 'bg-indigo-600 hover:bg-indigo-700'
          }`}
        >
          {saving ? 'Saving...' : 'Register Partner'}
        </button>
      </div>
    </div>
  );
};

export default AddPartner;
