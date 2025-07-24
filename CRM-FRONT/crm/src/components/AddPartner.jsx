import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import partnerService from '../services/partnerService';
import { toast } from 'sonner';
import {
  Document,
  Page,
  Text,
  StyleSheet,
  PDFDownloadLink,
} from '@react-pdf/renderer';
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





const PartnershipAgreementPDF = ({ partner }) => (
  <Document>
    <Page size="A4" style={styles.page}>
      <Text style={{ fontSize: 14, fontWeight: 'bold', marginBottom: 10, textAlign: 'center' }}>
        PARTNERSHIP AGREEMENT № __
      </Text>
      <Text>Baku city ..2025</Text>

      <Text style={{ marginTop: 10 }}>
        On one side, hereinafter referred to as the "Company," represented by its Executive Director, acting based on its Charter,
        Fancy az LL company, Tax ID (7889645047), and on the other side, hereinafter referred to as the "Owner," an individual acting on their own behalf,
        {` ${partner?.name || '____'} (Passport number ${partner?.passportNumber || '____'}, FIN: ${partner?.finCode || '____'}),`} collectively referred to as the "Parties,"
        enter into this Partnership Agreement under the following terms:
      </Text>

      <Text style={{ marginTop: 10, fontWeight: 'bold' }}>1. SUBJECT OF THE AGREEMENT</Text>
      <Text>
		1.1. The Owner transfers precious items, including gold jewelry and/or other valuable decorative items (individually or collectively referred to as "Items") owned by them to the Company for safekeeping during the term of this Agreement and authorizes the Company to lease or sell these Items to third parties as an intermediary. The Company agrees to pay the Owner the rental fees agreed upon in the Agreement.
	  
	  </Text>
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>2.MAIN TERMS</Text>
	  <Text>
		The terms used in this Agreement mean the following:
	  </Text>
	  <Text>
		2.1. Owner – Individual who legally owns or has disposal rights over the Items provided to the Company for lease or sale.
	  </Text>
	  <Text>
		2.2. Item(s) – Gold jewelry or decorative items provided by the Owner to the Company for lease or sale to third parties (Users).
	  </Text>
	  <Text>
		2.3. Lease Period – The period during which Items are leased by the Company to third parties, including the storage period when Items are not leased.
	  </Text>
	  <Text>
		2.4. Rental Fee – Amount payable by the Company to the Owner.
	  </Text>
		<Text>
			2.5. User – Third parties who lease the Items from the Company.
	  </Text>
	  <Text>
			2.6. Buyer – Third party purchasing Items through the Company based on a sales contract.
	  </Text>
	  <Text>
			2.7. Lease Duration – Period from the date Items are delivered to the User until returned to the Company.
	  </Text>
	  <Text>
			2.8. Lease Fee – Amount paid by the User to the Company under the lease agreement.
	  </Text>
	  <Text>
			2.9. Portal – Website or application managed by the Company where information about the Items is posted.
	  </Text>
	  <Text>
			2.10. Safekeeping – Storage of Items belonging to the Owner by the Company upon the Owner’s request.
	  </Text>
	  
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>3.ITEM DELIVERY PROCEDURE</Text>
	  
	  <Text>
			3.1. Owner must deliver Items in usable condition without visible defects. All details regarding the condition of Items must be documented.
	  </Text>
	  <Text>
			3.2. Owner must confirm the accuracy of inspection results conducted by Company representatives during Item delivery. Company representatives must answer Owner’s questions regarding these results.
	  </Text>
	  <Text>
			3.3. The value of Items must be recorded in the delivery act during the initial transfer.
	  </Text>
	  <Text>
			3.4. By providing Items, Owner grants the Company the right to lease or sell Items while retaining ownership rights.
	  </Text>
	  <Text>
			3.5. Owner consents to the Company leasing or selling Items to third parties.
	  </Text>
	  <Text>
			3.5. Owner consents to the Company leasing or selling Items to third parties.
	  </Text>
	  <Text>
			3.6. Delivery of Items to the Company is documented with an act that includes details such as condition, market value, appearance, and defects.
	  </Text>
	  <Text>
			3.7. Owner agrees that the Company may store Item photographs electronically, which form part of the Agreement.
	  </Text>
	  <Text>
			3.8. With Owner’s written consent, the Company may perform polishing or other maintenance on Items. Costs incurred will be borne by the Company if agreed in the act.
	  </Text>
	  
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>4.LEASING AND SALES PROCESS</Text>
	  
	  <Text>
		4.1. The Company may exhibit, lease, or sell Items to third parties according to its procedures. All related transactions are managed by the Company.
	  </Text>
	  <Text>
		4.2. The Company is responsible for tax obligations from rental income and deducts applicable taxes from the Owner’s revenue.
	  </Text>
	  <Text>
			4.3. Owner is not responsible for the Company’s tax, insurance, or financial obligations.
	  </Text>
	  <Text>
			4.4. Company undertakes to insure Items according to Azerbaijani law at its own expense.
	  </Text>
	  <Text>
			4.5. Company ensures timely insurance contract renewal and premium payments.
	  </Text>
	  
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>5.RIGHTS AND OBLIGATIONS</Text>
	  
	  <Text>
			5.1. Owner’s rights and obligations:
	  </Text>
	  <Text>
			•	Provide Items free from third-party claims;
	  </Text>
	  <Text>
			•	Provide proof of ownership during Item transfer;
	  </Text>
	  <Text>
			•	Ensure Items are not acquired illegally or under duress;
	  </Text>
	  <Text>
			•	Inform Company about known defects;
	  </Text>
	  <Text>
			•	Take responsibility for any third-party disputes regarding ownership;
	  </Text>
	  <Text>
			•	Be responsible for undisclosed defects;
	  </Text>
	  <Text>
			•	Comply with Article 7.1 for early retrieval of Items.
	  </Text>
	  
	  <Text>
			5.2. Company’s rights and obligations:
	  </Text>
	  <Text>
			•	Ensure complete safeguarding of Items;
	  </Text>
	  <Text>
			•	Pay the agreed fee to Owner on time.
	  </Text>
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>6.PAYMENT TERMS</Text>
	  <Text>
			6.1. Through the Portal, the Company:
	  </Text>
	  <Text>
			6.1.1 Pays Owner within one calendar month after receiving lease fees from Users;
	  </Text>
	  <Text>
			6.1.2 Transfers sales proceeds to Owner’s bank account or by other legal means.
	  </Text>
	  <Text>
			6.2. Owner receives 50% of rental income after tax deductions.
	  </Text>
	  <Text>
			6.3. Owner receives 90-95% of sales proceeds after deductions agreed upon by parties.
	  </Text>
	  <Text>
			6.4. Owner makes no payment for Item storage when not leased or sold.
	  </Text>
	  
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>7.	RETURN OF ITEMS</Text>

	  
	  <Text>
			7.1. Company returns undamaged Items to Owner upon six-month written notice or with compensation equal to five times the lease fee for shorter periods.
	  </Text>
	  <Text>
			7.2. Return of Items is documented by a final delivery act signed by both parties.
	  </Text>
	  <Text>
			7.3. Items are returned in documented condition, excluding normal wear and tear.
	  </Text>
	  <Text>
			7.4. Minor repairs during lease are done by the Company.
	  </Text>
	  <Text>
			7.2. Return of Items is documented by a final delivery act signed by both parties.
	  </Text>
	  <Text>
			7.2. Return of Items is documented by a final delivery act signed by both parties.
	  </Text>
	  
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>8.	AGREEMENT DURATION AND TERMINATION</Text>
	  
	  <Text>
			8.1. Agreement is valid for two years from signing, extendable for subsequent two-year terms unless terminated by 30-day notice.
	  </Text>
	  <Text>
			8.2. Immediate termination for fraud or breach is permitted.
	  </Text>
	  <Text>
			8.3. Upon termination, Items must be returned, and related leases end, though sales obligations continue.
	  </Text>
	  
	  
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>9. LIABILITY</Text>
	  
	  <Text>
			9.1. Parties bear liability for damages caused by incomplete performance.
	  </Text>
	  <Text>
			9.2. Owner is not liable if Company fails obligations.
	  </Text>
	  <Text>
			9.3. Owner may suspend obligations if Company deliberately obstructs Agreement performance.
	  </Text>
	  <Text>
			9.4. Company is responsible for loss or damage of Items during safekeeping.
	  </Text>
	  <Text>
			9.5. Concealed lease terms result in Company liability for Owner’s losses.
	  </Text>
	  <Text>
			9.6. Company is not liable for Item loss or significant damage by Users. 
	  </Text>
	  <Text>
			9.7. Company liability is limited to Item valuation.
	  </Text>
	  
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>10.	FORCE MAJEURE</Text>
	  
	  <Text>
			10.1. Parties are exempt from liability for obligations hindered by Force Majeure.
	  </Text>
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>11.	CONFIDENTIALITY</Text>
	  
	  <Text>
			11.1. Parties agree to maintain confidentiality for five years post-termination.
	  </Text>
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>12.	DISPUTE RESOLUTION</Text>
	  <Text>
			12.1. Azerbaijani law governs disputes, settled by negotiation or court.
	  </Text>
	  <Text style={{ marginTop: 10, fontWeight: 'bold' }}>13.	FINAL PROVISIONS</Text>
	  
	  <Text>
			13.1. Agreement supersedes prior arrangements.
	  </Text>
	  <Text>
			13.2. Portal terms are integral but subordinate to Agreement.
	  </Text>
	  <Text>
			13.3. Rights/obligations cannot be transferred without consent
	  </Text>
	  <Text>
			13.4. Partial invalidity does not void the entire Agreement.
	  </Text>
	  <Text>
			13.5. Company may unilaterally amend terms with 14-day notice.
	  </Text>
	  <Text>
			13.6. Amendments require written consent.
	  </Text>
	  <Text>
			13.7. Agreement executed in Azerbaijani in two equally valid originals, one for each party
	  </Text>
	  <Text>
			The Parties confirm by their signatures that they have concluded this Agreement:
	  </Text>
	  
      {/* You can continue adding full clauses like this */}
      
      <Text style={{ marginTop: 20, fontWeight: 'bold' }}>14. DETAILS OF THE PARTIES</Text>
      <Text style={{ marginTop: 5 }}>
        COMPANY: Fancy as LLC company
        {'\n'}Address : 
        {'\n'}Tax ID: 7889645047
        {'\n'}Executive Director: ---------- oglu
        {'\n'}Phone: ------------
      </Text>
      <Text style={{ marginTop: 10 }}>
        OWNER: {partner?.name || '____'}
        {'\n'}Passport №: {partner?.passportNumber || '____'}
        {'\n'}FIN: {partner?.finCode || '____'}
      </Text>

      <Text style={{ marginTop: 20 }}>Signature: ____________________________</Text>
    </Page>
  </Document>
);

const AddPartner = () => {
  const navigate = useNavigate();
  const [uploadedFile, setUploadedFile] = useState(null);
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


  

  const handleDownloadAgreement = async () => {
    try {
      
      const blob = await pdf(<PartnershipAgreementPDF partner={partner} />).toBlob();
      const url = URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `Contract-Partnership-Agreement.pdf`;
      document.body.appendChild(link);
      link.click();
      link.remove();
      URL.revokeObjectURL(url);
    } catch (error) {
       toast.error('Error generating contract PDF:', error);
       console.log('Error generating contract PDF:', error)
    }
  };

   const handleFileChange = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    try {
      setUploadedFile(file); // optional, if you want to keep track of the file locally
      toast.loading('Uploading document...');
      
      const response = await partnerService.uploadDocument(file);
      
      toast.dismiss(); // dismiss loading toast
      toast.success('Document uploaded successfully!');
      console.log('Upload response:', response);
      
      // You can update your partner state or do other things with the response here if needed
      
    } catch (error) {
      toast.dismiss();
      toast.error('Failed to upload document.');
      console.error('Upload error:', error);
    }
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

      <div className="mt-6 flex items-center gap-4">
  {/* Register Partner button */}
  <button
    onClick={registerPartner}
    disabled={saving}
    className={`px-6 py-2 rounded-md text-white ${
      saving ? 'bg-gray-400 cursor-not-allowed' : 'bg-indigo-600 hover:bg-indigo-700'
    }`}
  >
    {saving ? 'Saving...' : 'Register Partner'}
  </button>

  {/* PDFDownloadLink styled as white button */}
  <PDFDownloadLink
    document={<PartnershipAgreementPDF partner={partner} />}
    fileName={`Partnership_Agreement_${partner.name || 'partner'}.pdf`}
    className="px-6 py-2 rounded-md bg-white text-indigo-700 border border-indigo-700 hover:bg-indigo-50 focus:outline-none"
  >
    {({ loading }) => (loading ? 'Preparing document...' : 'Download Partnership Agreement')}
  </PDFDownloadLink>

  {/* Upload Document link/button */}
  <label
    htmlFor="upload-doc"
    className="cursor-pointer px-6 py-2 rounded-md bg-indigo-600 text-white hover:bg-indigo-700"
    title="Upload Document"
  >
    Upload Document
  </label>
  <input
    type="file"
    id="upload-doc"
    className="hidden"
    onChange={handleFileChange}
    accept=".pdf,.doc,.docx,.jpg,.jpeg,.png"
  />
</div>

      
          </div>
  );
};

export default AddPartner;
