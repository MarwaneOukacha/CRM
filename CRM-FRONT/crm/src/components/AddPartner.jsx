import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Save, PlusCircle, Trash2 } from 'lucide-react';
import partnerService from '../services/partnerService';

const Notification = ({ message, type, onDismiss }) => {
  if (!message) return null;
  const baseClasses = 'fixed top-5 right-5 p-4 rounded-lg shadow-lg text-white transition-opacity duration-300 z-50';
  const typeClasses = { success: 'bg-green-500', error: 'bg-red-500' };
  React.useEffect(() => {
    const timer = setTimeout(onDismiss, 3000);
    return () => clearTimeout(timer);
  }, [onDismiss]);
  return <div className={`${baseClasses} ${typeClasses[type]}`}>{message}</div>;
};

const FormInput = ({ id, label, ...props }) => (
  <div>
    <label htmlFor={id} className="block text-sm font-medium text-gray-600 dark:text-gray-300 mb-1">{label}</label>
    <input id={id} {...props} className="w-full p-2.5 rounded-md border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 text-gray-900 dark:text-gray-100 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition" />
  </div>
);

const AddPartner = () => {
  const navigate = useNavigate();
  const [partner, setPartner] = useState({
    name: '',
    email: '',
    phone: '',
    address: '',
    notes: '',
    company: {
      name: '',
      taxId: '',
      address: '',
      contactEmail: '',
      contactPhone: ''
    },
    contracts: []
  });
  const [saving, setSaving] = useState(false);
  const [notification, setNotification] = useState({ message: '', type: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPartner((prev) => ({ ...prev, [name]: value }));
  };

  const handleCompanyChange = (e) => {
    const { name, value } = e.target;
    setPartner((prev) => ({
      ...prev,
      company: { ...prev.company, [name]: value }
    }));
  };

  const handleContractChange = (index, field, value) => {
    const contracts = [...partner.contracts];
    contracts[index] = { ...contracts[index], [field]: value };
    setPartner((prev) => ({ ...prev, contracts }));
  };

  const addContract = () => {
    setPartner((prev) => ({ ...prev, contracts: [...prev.contracts, {}] }));
  };

  const removeContract = (index) => {
    const contracts = [...partner.contracts];
    contracts.splice(index, 1);
    setPartner((prev) => ({ ...prev, contracts }));
  };

  const registerPartner = async () => {
    setSaving(true);
    try {
      await partnerService.registerPartner(partner);
      setNotification({ message: 'Partner registered successfully!', type: 'success' });
      setTimeout(() => navigate('/partners'), 1500);
    } catch (err) {
      setNotification({ message: 'Failed to register partner.', type: 'error' });
      console.error(err);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="bg-gray-100 dark:bg-gray-900 min-h-screen p-6">
      <Notification message={notification.message} type={notification.type} onDismiss={() => setNotification({ message: '', type: '' })} />
      <div className="max-w-4xl mx-auto space-y-6">
        <div className="flex items-center justify-between">
          <button onClick={() => navigate(-1)} className="flex items-center text-sm font-semibold text-blue-600 hover:text-blue-800 dark:text-blue-400 dark:hover:text-blue-300 transition">
            <ArrowLeft size={16} className="mr-2" /> Back
          </button>
          <button onClick={registerPartner} disabled={saving} className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg font-semibold shadow-md hover:shadow-lg disabled:opacity-50">
            <Save size={18} /> {saving ? "Saving..." : "Save Partner"}
          </button>
        </div>

        {/* Partner Info */}
        <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-md space-y-4">
          <h3 className="text-lg font-semibold text-gray-800 dark:text-gray-100">Partner Information</h3>
          <FormInput id="name" name="name" label="Name" value={partner.name} onChange={handleChange} required />
          <FormInput id="email" name="email" label="Email" value={partner.email} onChange={handleChange} type="email" />
          <FormInput id="phone" name="phone" label="Phone" value={partner.phone} onChange={handleChange} />
          <FormInput id="address" name="address" label="Address" value={partner.address} onChange={handleChange} />
          <FormInput id="notes" name="notes" label="Notes" value={partner.notes} onChange={handleChange} />
          <FormInput id="passportSeries" name="passportSeries" label="Passport Series" value={partner.passportSeries || ''} onChange={handleChange} />
          <FormInput id="passportNumber" name="passportNumber" label="Passport Number" value={partner.passportNumber || ''} onChange={handleChange} />
          <FormInput id="finCode" name="finCode" label="FIN Code" value={partner.finCode || ''} onChange={handleChange} />
          <FormInput id="receivingBankName" name="receivingBankName" label="Receiving Bank Name" value={partner.receivingBankName || ''} onChange={handleChange} />
          <FormInput id="receivingBankCurrency" name="receivingBankCurrency" label="Receiving Bank Currency" value={partner.receivingBankCurrency || ''} onChange={handleChange} />
          <FormInput id="bankTIN" name="bankTIN" label="Bank TIN" value={partner.bankTIN || ''} onChange={handleChange} />
          <FormInput id="bankSwiftCode" name="bankSwiftCode" label="Bank SWIFT Code" value={partner.bankSwiftCode || ''} onChange={handleChange} />
          <FormInput id="bankAccountNumber" name="bankAccountNumber" label="Bank Account Number" value={partner.bankAccountNumber || ''} onChange={handleChange} />

        </div>

        {/* Company Info */}
        <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-md space-y-4">
          <h3 className="text-lg font-semibold text-gray-800 dark:text-gray-100">Company Information</h3>
          <FormInput id="companyName" name="name" label="Company Name" value={partner.company.name} onChange={handleCompanyChange} required />
          <FormInput id="taxId" name="taxId" label="Tax ID" value={partner.company.taxId} onChange={handleCompanyChange} />
          <FormInput id="companyAddress" name="address" label="Company Address" value={partner.company.address} onChange={handleCompanyChange} />
          <FormInput id="contactEmail" name="contactEmail" label="Contact Email" value={partner.company.contactEmail} onChange={handleCompanyChange} />
          <FormInput id="contactPhone" name="contactPhone" label="Contact Phone" value={partner.company.contactPhone} onChange={handleCompanyChange} />
          <FormInput id="companyBankName" name="companyBankName" label="Company Bank Name" value={partner.company.companyBankName || ''} onChange={handleCompanyChange} />
          <FormInput id="companyBankCurrency" name="companyBankCurrency" label="Company Bank Currency" value={partner.company.companyBankCurrency || ''} onChange={handleCompanyChange} />
          <FormInput id="bankTIN" name="bankTIN" label="Company Bank TIN" value={partner.company.bankTIN || ''} onChange={handleCompanyChange} />
          <FormInput id="bankSwiftCode" name="bankSwiftCode" label="Company Bank SWIFT Code" value={partner.company.bankSwiftCode || ''} onChange={handleCompanyChange} />
          <FormInput id="bankAccountNumber" name="bankAccountNumber" label="Company Bank Account Number" value={partner.company.bankAccountNumber || ''} onChange={handleCompanyChange} />

        </div>

        {/* Contracts */}
        <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow-md">
  <h3 className="text-lg font-semibold text-gray-800 dark:text-gray-100 mb-4">Contracts</h3>
  <div className="space-y-3">
    {partner.contracts.map((contract, index) => (
      <div key={index} className="p-3 border border-gray-300 dark:border-gray-600 rounded-lg space-y-2 bg-gray-50 dark:bg-gray-700">
        <div className="flex justify-between items-center">
          <span className="font-semibold text-gray-800 dark:text-gray-100">Contract {index + 1}</span>
          <button onClick={() => removeContract(index)} className="text-red-500 hover:text-red-700 dark:hover:text-red-400">
            <Trash2 size={16} />
          </button>
        </div>
        <FormInput
          id={`productId-${index}`}
          value={contract.productId || ''}
          onChange={(e) => handleContractChange(index, 'productId', e.target.value)}
          label="Product ID"
        />
        <FormInput
          id={`saleCompanyPercent-${index}`}
          value={contract.saleCompanyPercent || ''}
          onChange={(e) => handleContractChange(index, 'saleCompanyPercent', e.target.value)}
          label="Sale Company Percent"
          type="number"
        />
        <FormInput
          id={`salePartnerPercent-${index}`}
          value={contract.salePartnerPercent || ''}
          onChange={(e) => handleContractChange(index, 'salePartnerPercent', e.target.value)}
          label="Sale Partner Percent"
          type="number"
        />
        <FormInput
          id={`validFrom-${index}`}
          value={contract.validFrom || ''}
          onChange={(e) => handleContractChange(index, 'validFrom', e.target.value)}
          label="Valid From"
          type="date"
        />
        <FormInput
          id={`validTo-${index}`}
          value={contract.validTo || ''}
          onChange={(e) => handleContractChange(index, 'validTo', e.target.value)}
          label="Valid To"
          type="date"
        />
        <FormInput
          id={`damageCompanyCompensation-${index}`}
          value={contract.damageCompanyCompensation || ''}
          onChange={(e) => handleContractChange(index, 'damageCompanyCompensation', e.target.value)}
          label="Damage Company Compensation"
          type="number"
        />
        <FormInput
          id={`lossCompanyCompensation-${index}`}
          value={contract.lossCompanyCompensation || ''}
          onChange={(e) => handleContractChange(index, 'lossCompanyCompensation', e.target.value)}
          label="Loss Company Compensation"
          type="number"
        />
        <FormInput
          id={`partnerTakebackFeePercent-${index}`}
          value={contract.partnerTakebackFeePercent || ''}
          onChange={(e) => handleContractChange(index, 'partnerTakebackFeePercent', e.target.value)}
          label="Partner Takeback Fee Percent"
          type="number"
        />
        <FormInput
          id={`rentCompanyPercent-${index}`}
          value={contract.rentCompanyPercent || ''}
          onChange={(e) => handleContractChange(index, 'rentCompanyPercent', e.target.value)}
          label="Rent Company Percent"
          type="number"
        />
        <FormInput
          id={`rentPartnerPercent-${index}`}
          value={contract.rentPartnerPercent || ''}
          onChange={(e) => handleContractChange(index, 'rentPartnerPercent', e.target.value)}
          label="Rent Partner Percent"
          type="number"
        />
        <FormInput
          id={`returnFeePercent-${index}`}
          value={contract.returnFeePercent || ''}
          onChange={(e) => handleContractChange(index, 'returnFeePercent', e.target.value)}
          label="Return Fee Percent"
          type="number"
        />
        <FormInput
          id={`returnFeePayer-${index}`}
          value={contract.returnFeePayer || ''}
          onChange={(e) => handleContractChange(index, 'returnFeePayer', e.target.value)}
          label="Return Fee Payer"
        />
        <FormInput
          id={`contractNotes-${index}`}
          value={contract.notes || ''}
          onChange={(e) => handleContractChange(index, 'notes', e.target.value)}
          label="Contract Notes"
        />

      </div>
    ))}
  </div>
  <button onClick={addContract} type="button" className="mt-4 flex items-center gap-2 text-sm text-blue-600 dark:text-blue-400 font-semibold hover:text-blue-800 dark:hover:text-blue-300 transition">
    <PlusCircle size={16} /> Add Contract
  </button>
</div>
      </div>
    </div>
  );
};

export default AddPartner;
