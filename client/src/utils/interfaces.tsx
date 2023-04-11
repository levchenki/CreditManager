export interface IClient {
  id: number
  firstname: string
  lastname: string
  patronymic?: string
  maritalStatus: MaritalStatus
  phone: string
  passport: IPassport
  employmentInfo: IEmploymentInfo[]
}

export enum MaritalStatus {
  MARRIED = 'MARRIED',
  NOT_MARRIED = 'NOT_MARRIED',
}

export interface IPassport {
  id: number
  series: string
  number: string
  divisionCode: string
  issuedBy: string
  issuedDate: string
  registrationAddress: string
}

export interface IEmploymentInfo {
  id: number
  organizationName: string
  position: string
  startDate: string
  endDate?: string
}

export interface ICreditApplication {
  id: number,
  desiredMoney: number,
  status: CreditStatus,
  client: IClient
}

export enum CreditStatus {
  WAITING = "WAITING",
  APPROVED = "APPROVED",
  DENIED = "DENIED"
}

export interface ICreditContract {
  id: number
  daysCount: number
  approvedMoney: number
  signatureDate: Date | null
  creditApplication: ICreditApplication
  signed: boolean
}