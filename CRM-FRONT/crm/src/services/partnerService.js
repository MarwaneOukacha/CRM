import axiosInstance from "../utils/axiosInstance";

const BASE_URL = "/partners";

const partnerService = {
  // POST /partners/register
  registerPartner: async (partnerData) => {
    const response = await axiosInstance.post(`${BASE_URL}/register`, partnerData);
    return response.data;
  },

  // GET /partners/{partnerId}
  getPartnerById: async (partnerId) => {
    const response = await axiosInstance.get(`${BASE_URL}/${partnerId}`);
    return response.data;
  },

  // PUT /partners/{partnerId}
  updatePartner: async (partnerId, updatedData) => {
    const response = await axiosInstance.put(`${BASE_URL}/${partnerId}`, updatedData);
    return response.data;
  },

  // GET /partners?companyName=...&status=...&page=0&size=10
  searchPartners: async (criteria = {}, page = 0, size = 10) => {
    const params = {
      ...criteria,
      page,
      size
    };
    const response = await axiosInstance.get(BASE_URL, { params });
    return response.data;
  }
};

export default partnerService;
