import React from "react";
import { Document, Page, Text, View, StyleSheet } from "@react-pdf/renderer";

const styles = StyleSheet.create({
  page: { padding: 30, fontSize: 12, fontFamily: "Helvetica" },
  header: { flexDirection: "row", justifyContent: "space-between", marginBottom: 20 },
  companyTitle: { fontSize: 18, fontWeight: "bold" },
  headerRight: { alignItems: "flex-end" },
  sectionHeader: { fontWeight: "bold", fontSize: 14, marginBottom: 5 },
  providerCustomerSection: { flexDirection: "row", justifyContent: "space-between", marginBottom: 20 },
  providerSection: { width: "48%" },
  customerSection: { width: "48%" },
  invoiceSection: { marginBottom: 20 },
  invoiceTitle: { fontSize: 16, fontWeight: "bold", marginBottom: 10 },
  invoiceDetailsTable: { flexDirection: "row", marginBottom: 10 },
  invoiceDetailCell: { padding: 5, border: "1px solid #000" },
  invoiceDetailHeader: { fontWeight: "bold" },
  invoiceDetailValue: { marginTop: 2 },
  servicesSection: { marginTop: 20 },
  servicesTitle: { fontSize: 16, fontWeight: "bold", marginBottom: 10 },
  table: { borderWidth: 1, borderColor: "#000" },
  tableHeader: { flexDirection: "row", backgroundColor: "#eee" },
  tableHeaderCell: { padding: 5, borderRight: "1px solid #000", fontWeight: "bold" },
  tableCell: { padding: 5, borderTop: "1px solid #000", borderRight: "1px solid #000" },
  descriptionCol: { width: "50%" },
  valueCol: { width: "50%" },
});

function ContractPDF({ partner, order }) {
  const invoiceDate = new Date().toLocaleDateString();

  return (
    <Document>
      <Page size="A4" style={styles.page}>
        {/* Header */}
        <View style={styles.header}>
          <Text style={styles.companyTitle}>{partner.company?.name || "Company"}</Text>
          <View style={styles.headerRight}>
            <Text>{partner.company?.name || "Company"}</Text>
            <Text>Tax ID: {partner.company?.taxId || "-"}</Text>
          </View>
        </View>

        {/* Provider & Customer */}
        <View style={styles.providerCustomerSection}>
          <View style={styles.providerSection}>
            <Text style={styles.sectionHeader}>PROVIDER</Text>
            <Text>{partner.company?.name || "Company"}</Text>
            <Text>Tax ID: {partner.company?.taxId || "-"}</Text>
            <Text>{partner.company?.address || "-"}</Text>
          </View>
          <View style={styles.customerSection}>
            <Text style={styles.sectionHeader}>CUSTOMER</Text>
            <Text>{order.customerCode}</Text>
            <Text>{order.customerEmail}</Text>
            <Text>Status: {order.status}</Text>
          </View>
        </View>

        {/* Order Details */}
        <View style={styles.invoiceSection}>
          <Text style={styles.invoiceTitle}>Order Details</Text>
          <View style={styles.invoiceDetailsTable}>
            <View style={[styles.invoiceDetailCell, { width: "50%" }]}>
              <Text style={styles.invoiceDetailHeader}>Date</Text>
              <Text style={styles.invoiceDetailValue}>{invoiceDate}</Text>
            </View>
            <View style={[styles.invoiceDetailCell, { width: "50%" }]}>
              <Text style={styles.invoiceDetailHeader}>Order Code</Text>
              <Text style={styles.invoiceDetailValue}>{order.code}</Text>
            </View>
          </View>
        </View>

        {/* Order Items */}
        <View style={styles.servicesSection}>
          <Text style={styles.servicesTitle}>Order Items</Text>
          <View style={styles.table}>
            <View style={styles.tableHeader}>
              <Text style={[styles.tableHeaderCell, styles.descriptionCol]}>Product</Text>
              <Text style={[styles.tableHeaderCell, styles.valueCol, { borderRight: "none" }]}>Quantity</Text>
            </View>

            {order.orderItems?.map((item, index) => (
              <View style={styles.tableRow} key={index}>
                <Text style={[styles.tableCell, styles.descriptionCol]}>{item.productCode}</Text>
                <Text style={[styles.tableCell, styles.valueCol, { borderRight: "none" }]}>{item.quantity}</Text>
              </View>
            ))}
          </View>
        </View>

        {/* Payment Details */}
        <View style={styles.servicesSection}>
          <Text style={styles.servicesTitle}>Payment Summary</Text>
          <View style={styles.table}>
            <View style={styles.tableRow}>
              <Text style={[styles.tableCell, styles.descriptionCol]}>Total Price</Text>
              <Text style={[styles.tableCell, styles.valueCol, { borderRight: "none" }]}>${order.totalPrice?.toFixed(2)}</Text>
            </View>
            <View style={styles.tableRow}>
              <Text style={[styles.tableCell, styles.descriptionCol]}>Deposit Paid</Text>
              <Text style={[styles.tableCell, styles.valueCol, { borderRight: "none" }]}>${order.depositPaid?.toFixed(2)}</Text>
            </View>
            <View style={styles.tableRow}>
              <Text style={[styles.tableCell, styles.descriptionCol]}>Total Debt</Text>
              <Text style={[styles.tableCell, styles.valueCol, { borderRight: "none" }]}>${order.totalDebt?.toFixed(2)}</Text>
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

export default ContractPDF;
