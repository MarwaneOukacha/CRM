import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ArrowLeft, Download, Save } from 'lucide-react';
import partnerService from '../services/partnerService';
import productService from '../services/productService';
import { pdf, Document, Page, Text, View, StyleSheet } from '@react-pdf/renderer';

// Your styles from the example
const styles = StyleSheet.create({
  page: {
    fontFamily: 'Helvetica',
    fontSize: 11,
    padding: 40,
    backgroundColor: '#ffffff',
    color: '#333333',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: 30,
    paddingBottom: 20,
    borderBottomWidth: 1,
    borderBottomColor: '#e0e0e0',
    borderBottomStyle: 'solid',
  },
  companyTitle: {
    fontSize: 32,
    fontWeight: 'bold',
    color: '#333333',
    letterSpacing: 2,
  },
  headerRight: {
    alignItems: 'flex-end',
    fontSize: 10,
    color: '#666666',
  },
  headerRightText: {
    marginBottom: 2,
  },
  providerCustomerSection: {
    flexDirection: 'row',
    marginBottom: 30,
  },
  providerSection: {
    flex: 1,
    marginRight: 40,
  },
  customerSection: {
    flex: 1,
  },
  sectionHeader: {
    fontSize: 12,
    fontWeight: 'bold',
    color: '#666666',
    marginBottom: 15,
    textTransform: 'uppercase',
    letterSpacing: 1,
  },
  providerText: {
    fontSize: 10,
    marginBottom: 3,
    color: '#333333',
  },
  providerCompany: {
    fontSize: 12,
    fontWeight: 'bold',
    marginBottom: 3,
    color: '#333333',
  },
  invoiceSection: {
    marginBottom: 30,
  },
  invoiceTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#333333',
    marginBottom: 20,
  },
  invoiceDetailsTable: {
    flexDirection: 'row',
    marginBottom: 30,
  },
  invoiceDetailCell: {
    border: '1px solid #cccccc',
    padding: 8,
    minHeight: 40,
  },
  invoiceDetailHeader: {
    backgroundColor: '#f5f5f5',
    fontWeight: 'bold',
    fontSize: 10,
    borderBottom: '1px solid #cccccc',
    paddingBottom: 5,
    marginBottom: 5,
  },
  invoiceDetailValue: {
    fontSize: 10,
    color: '#333333',
  },
  servicesSection: {
    marginBottom: 30,
  },
  servicesTitle: {
    fontSize: 14,
    fontWeight: 'bold',
    color: '#333333',
    marginBottom: 15,
  },
  table: {
    border: '1px solid #cccccc',
  },
  tableHeader: {
    flexDirection: 'row',
    backgroundColor: '#f5f5f5',
    borderBottom: '1px solid #cccccc',
  },
  tableHeaderCell: {
    padding: 10,
    fontSize: 10,
    fontWeight: 'bold',
    color: '#333333',
    textAlign: 'center',
    borderRight: '1px solid #cccccc',
  },
  tableRow: {
    flexDirection: 'row',
    borderBottom: '1px solid #cccccc',
  },
  tableCell: {
    padding: 10,
    fontSize: 10,
    color: '#333333',
    textAlign: 'center',
    borderRight: '1px solid #cccccc',
  },
  descriptionCol: { width: '70%' },
  valueCol: { width: '30%' },
});

// PDF document component
function ContractPDF({ partner, product, contract }) {
  const invoiceDate = new Date().toLocaleDateString();

  return (
    <Document>
      <Page size="A4" style={styles.page}>
        {/* Header */}
        <View style={styles.header}>
          <Text style={styles.companyTitle}>{partner.company?.name || 'Company'}</Text>
          <View style={styles.headerRight}>
            <Text style={styles.headerRightText}>{partner.company?.name || 'Company'}</Text>
            <Text style={styles.headerRightText}>Tax ID: {partner.company?.taxId || '-'}</Text>
          </View>
        </View>

        {/* Provider and Partner */}
        <View style={styles.providerCustomerSection}>
          <View style={styles.providerSection}>
            <Text style={styles.sectionHeader}>PROVIDER</Text>
            <Text style={styles.providerCompany}>{partner.company?.name || 'Company'}</Text>
            <Text style={styles.providerText}>Tax ID: {partner.company?.taxId || '-'}</Text>
            <Text style={styles.providerText}>{partner.company?.address || '-'}</Text>
          </View>
          <View style={styles.customerSection}>
            <Text style={styles.sectionHeader}>PARTNER</Text>
            <Text style={styles.providerText}>{partner.name || '-'}</Text>
            <Text style={styles.providerText}>{partner.email || '-'}</Text>
            <Text style={styles.providerText}>{partner.phone || '-'}</Text>
            <Text style={styles.providerText}>{partner.address || '-'}</Text>
          </View>
        </View>

        {/* Invoice Details */}
        <View style={styles.invoiceSection}>
          <Text style={styles.invoiceTitle}>Contract Details</Text>
          <View style={styles.invoiceDetailsTable}>
            <View style={[styles.invoiceDetailCell, { width: '50%' }]}>
              <Text style={styles.invoiceDetailHeader}>Date</Text>
              <Text style={styles.invoiceDetailValue}>{invoiceDate}</Text>
            </View>
            <View style={[styles.invoiceDetailCell, { width: '50%' }]}>
              <Text style={styles.invoiceDetailHeader}>Product</Text>
              <Text style={styles.invoiceDetailValue}>{product?.name || '-'}</Text>
            </View>
          </View>
        </View>

        {/* Contract Details Table */}
        <View style={styles.servicesSection}>
          <Text style={styles.servicesTitle}>Product & Contract Terms</Text>
          <View style={styles.table}>
            <View style={styles.tableHeader}>
              <Text style={[styles.tableHeaderCell, styles.descriptionCol]}>Description</Text>
              <Text style={[styles.tableHeaderCell, styles.valueCol, { borderRight: 'none' }]}>Value</Text>
            </View>

            <View style={styles.tableRow}>
              <Text style={[styles.tableCell, styles.descriptionCol]}>Product Code</Text>
              <Text style={[styles.tableCell, styles.valueCol, { borderRight: 'none' }]}>{product?.code || '-'}</Text>
            </View>
            <View style={styles.tableRow}>
              <Text style={[styles.tableCell, styles.descriptionCol]}>Price</Text>
              <Text style={[styles.tableCell, styles.valueCol, { borderRight: 'none' }]}>{product?.price} $</Text>
            </View>
            <View style={styles.tableRow}>
              <Text style={[styles.tableCell, styles.descriptionCol]}>Sale Company Percent</Text>
              <Text style={[styles.tableCell, styles.valueCol, { borderRight: 'none' }]}>{contract.saleCompanyPercent}%</Text>
            </View>
            <View style={styles.tableRow}>
              <Text style={[styles.tableCell, styles.descriptionCol]}>Sale Partner Percent</Text>
              <Text style={[styles.tableCell, styles.valueCol, { borderRight: 'none' }]}>{contract.salePartnerPercent}%</Text>
            </View>
            <View style={styles.tableRow}>
              <Text style={[styles.tableCell, styles.descriptionCol]}>Valid From</Text>
              <Text style={[styles.tableCell, styles.valueCol, { borderRight: 'none' }]}>{contract.validFrom}</Text>
            </View>
            <View style={styles.tableRow}>
              <Text style={[styles.tableCell, styles.descriptionCol]}>Valid To</Text>
              <Text style={[styles.tableCell, styles.valueCol, { borderRight: 'none' }]}>{contract.validTo}</Text>
            </View>
          </View>
        </View>

        {/* Signature */}
        <View style={{ marginTop: 40 }}>
          <Text>Signature: ____________________________</Text>
        </View>
      </Page>
    </Document>
  );
}

const FormInput = ({ label, name, value, onChange, type = 'text' }) => (
  <div className="mb-2">
    <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">{label}</label>
    <input
      type={type}
      name={name}
      value={value || ''}
      onChange={onChange}
      className="w-full p-2 border rounded dark:bg-gray-800 dark:border-gray-600 dark:text-white"
    />
  </div>
);

const PartnerDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [partner, setPartner] = useState(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  const handleDownloadContract = async (contract) => {
    try {
      const productResponse = await productService.getByCode(contract.productId);
      const product = productResponse.data;

      const blob = await pdf(<ContractPDF partner={partner} product={product} contract={contract} />).toBlob();

      const url = URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `Contract-${contract.productId}.pdf`;
      document.body.appendChild(link);
      link.click();
      link.remove();
      URL.revokeObjectURL(url);
    } catch (error) {
      console.error('Error generating contract PDF:', error);
    }
  };

  useEffect(() => {
    const fetchPartner = async () => {
      try {
        const data = await partnerService.getPartnerById(id);
        setPartner(data);
      } catch (err) {
        console.error('Failed to fetch partner details:', err);
      } finally {
        setLoading(false);
      }
    };
    fetchPartner();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPartner((prev) => ({ ...prev, [name]: value }));
  };

  const handleCompanyChange = (e) => {
    const { name, value } = e.target;
    setPartner((prev) => ({
      ...prev,
      company: { ...prev.company, [name]: value },
    }));
  };

  const handleContractChange = (index, field, value) => {
    const updatedContracts = [...(partner.company.contracts || [])];
    updatedContracts[index] = { ...updatedContracts[index], [field]: value };
    setPartner((prev) => ({
      ...prev,
      company: { ...prev.company, contracts: updatedContracts },
    }));
  };

  const saveChanges = async () => {
    setSaving(true);
    try {
      await partnerService.updatePartner(id, partner);
      navigate('/partners');
    } catch (err) {
      console.error('Failed to save changes:', err);
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="text-center p-6">Loading partner details...</div>;
  if (!partner) return <div className="text-center p-6 text-red-500">Partner not found.</div>;

  return (
    <div className="bg-gray-100 dark:bg-gray-900 min-h-screen p-6">
      <div className="max-w-4xl mx-auto space-y-6">
        <div className="flex items-center justify-between">
          <button
            onClick={() => navigate(-1)}
            className="flex items-center text-sm font-semibold text-blue-600 hover:text-blue-800 dark:text-blue-400 dark:hover:text-blue-300 transition"
          >
            <ArrowLeft size={16} className="mr-2" /> Back
          </button>
          <button
            onClick={saveChanges}
            disabled={saving}
            className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg font-semibold shadow disabled:opacity-50"
          >
            <Save size={18} /> {saving ? 'Saving...' : 'Save Changes'}
          </button>
        </div>

        <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow space-y-2">
          <h3 className="text-lg font-semibold">Partner Information</h3>
          <FormInput label="Name" name="name" value={partner.name} onChange={handleChange} />
          <FormInput label="Email" name="email" value={partner.email} onChange={handleChange} />
          <FormInput label="Phone" name="phone" value={partner.phone} onChange={handleChange} />
          <FormInput label="Address" name="address" value={partner.address} onChange={handleChange} />
        </div>

        <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow space-y-2">
          <h3 className="text-lg font-semibold">Company Information</h3>
          <FormInput label="Company Name" name="name" value={partner.company?.name} onChange={handleCompanyChange} />
          <FormInput label="Tax ID" name="taxId" value={partner.company?.taxId} onChange={handleCompanyChange} />
          <FormInput label="Company Address" name="address" value={partner.company?.address} onChange={handleCompanyChange} />
          <FormInput label="Contact Email" name="contactEmail" value={partner.company?.contactEmail} onChange={handleCompanyChange} />
          <FormInput label="Contact Phone" name="contactPhone" value={partner.company?.contactPhone} onChange={handleCompanyChange} />
        </div>

        <div className="bg-white dark:bg-gray-800 p-6 rounded-xl shadow space-y-4">
          <h3 className="text-lg font-semibold">Contracts</h3>
          {partner.contracts?.length > 0 ? (
            partner.contracts.map((contract, index) => (
              <div
                key={index}
                className="border border-gray-300 dark:border-gray-600 rounded-lg bg-gray-50 dark:bg-gray-700 p-4 space-y-4 shadow-sm"
              >
                <div className="flex items-center justify-between">
                  <div className="font-semibold text-base text-gray-800 dark:text-white">
                    Contract for Product: {contract.productId}
                  </div>
                  <button
                    onClick={() => handleDownloadContract(contract)}
                    className="flex items-center gap-1 text-blue-600 hover:text-blue-800 dark:text-blue-400 dark:hover:text-blue-300 text-sm font-medium"
                  >
                    <Download size={16} /> Download PDF
                  </button>
                </div>
              </div>
            ))
          ) : (
            <div className="text-gray-600 dark:text-gray-300">No contracts available.</div>
          )}
        </div>
      </div>
    </div>
  );
};

export default PartnerDetails;
