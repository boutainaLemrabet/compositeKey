import { IWithIdString } from 'app/shared/model/with-id-string.model';

export interface IWithIdStringDetails {
  withIdStringId?: string;
  name?: string;
  withIdString?: IWithIdString;
}

export class WithIdStringDetails implements IWithIdStringDetails {
  constructor(public withIdStringId?: string, public name?: string, public withIdString?: IWithIdString) {}
}
