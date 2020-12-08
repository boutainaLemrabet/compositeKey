import { IWithIdStringDetails } from 'app/shared/model/with-id-string-details.model';

export interface IWithIdString {
  id?: string;
  withIdStringDetails?: IWithIdStringDetails;
}

export class WithIdString implements IWithIdString {
  constructor(public id?: string, public withIdStringDetails?: IWithIdStringDetails) {}
}
