import {ClientsPage} from "../../pages/clients/ClientsPage";
import {CreditApplicationsPage} from "../../pages/applications/CreditApplicationsPage";
import {CreditContractsPage} from "../../pages/contracts/CreditContractsPage";
import {ClientPage} from "../../pages/clients/ClientPage";
import {NewClientPage} from "../../pages/clients/NewClientPage";
import {NewCreditApplication} from "../../pages/applications/NewCreditApplication.";
import {CreditContractPage} from "../../pages/contracts/CreditContractPage";

interface IRoutes {
  path: RoutePath
  element: JSX.Element
}

enum RoutePath {
  clients = '/clients',
  newClient = '/clients/new',
  creditApplications = '/applications',
  creditContracts = '/contracts',
  client = '/clients/:id',
  newCreditApplication = '/clients/:id/new_application',
  creditContract = '/contracts/:id'
}

export const routes: readonly IRoutes[] = [
  {
    path: RoutePath.clients,
    element: <ClientsPage/>
  },
  {
    path: RoutePath.client,
    element: <ClientPage/>
  },
  {
    path: RoutePath.newClient,
    element: <NewClientPage/>
  },
  {
    path: RoutePath.creditApplications,
    element: <CreditApplicationsPage/>
  },
  {
    path: RoutePath.creditContracts,
    element: <CreditContractsPage/>
  },
  {
    path: RoutePath.newCreditApplication,
    element: <NewCreditApplication/>
  },
  {
    path: RoutePath.creditContract,
    element: <CreditContractPage/>
  }
]