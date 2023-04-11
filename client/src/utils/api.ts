import {API_URL} from "./config";
import axios from "axios";
import {IClient, ICreditApplication, ICreditContract} from "./interfaces";


export const getClients = async (param: string = '') => {
  const query = `${API_URL}/clients/all${param}`
  try {
    const response = await axios.get<IClient[]>(query)
    return await response.data;
  } catch (e) {
  }
}

export const getClientById = async (id: number) => {
  const query = `${API_URL}/clients/${id}`
  const response = await axios.get<IClient>(query)
  return response.data;
}

export const postClient = async (formData: FormData) => {
  const query = `${API_URL}/clients`
  return await axios.post(query, formData, {
    headers: {'Content-Type': 'application/json'}
  });
}

export const getCreditApplications = async () => {
  const query = `${API_URL}/applications/all`
  try {
    const response = await axios.get<ICreditApplication[]>(query)
    return await response.data;
  } catch (e) {
  }
}

export const getCreditApplicationsByClient = async (clientId: number) => {
  const query = `${API_URL}/applications/all?clientId=${clientId}`
  const response = await axios.get<ICreditApplication[]>(query)
  return response.data;
}

export const createApplication = async (formData: FormData) => {
  const query = `${API_URL}/applications`
  return await axios.post(query, formData, {
    headers: {'Content-Type': 'application/json'}
  });
}

export const getCreditContracts = async () => {
  const query = `${API_URL}/contracts/all`
  return await axios.get<ICreditContract[]>(query)
}

export const getCreditContractByApplication = async (applicationId: number) => {
  const query = `${API_URL}/contracts/?application=${applicationId}`
  return await axios.get<ICreditContract>(query)
}

export const getCreditContractById = async (contractId: number) => {
  const query = `${API_URL}/contracts/${contractId}`
  return await axios.get<ICreditContract>(query)
}

export const signCreditContract = async (contractId: number) => {
  const query = `${API_URL}/contracts/${contractId}`
  return await axios.post<ICreditContract>(query)
}