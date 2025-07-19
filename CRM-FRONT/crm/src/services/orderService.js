import axiosInstance from '../utils/axiosInstance';

const ORDER_API = '/orders';

const createOrder = async (orderData) => {
  try {
    const response = await axiosInstance.post(ORDER_API, orderData);
    return response.data;
  } catch (error) {
    throw error;
  }
};

const getOrderById = async (id) => {
  try {
    const response = await axiosInstance.get(`${ORDER_API}/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

const searchOrders = async (searchCriteria, page = 0, size = 10) => {
  try {
    const params = {
      ...searchCriteria,
      page,
      size
    };
    const response = await axiosInstance.get(`${ORDER_API}/search`, { params });
    return response.data;
  } catch (error) {
    throw error;
  }
};

const orderService = {
  createOrder,
  getOrderById,
  searchOrders,
};

export default orderService;
