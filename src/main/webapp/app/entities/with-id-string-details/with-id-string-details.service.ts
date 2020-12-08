import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from '../../app.constants';
import { createRequestOption } from '../../core/request/request-util';
import { IWithIdStringDetails } from '../../shared/model/with-id-string-details.model';

type EntityResponseType = HttpResponse<IWithIdStringDetails>;
type EntityArrayResponseType = HttpResponse<IWithIdStringDetails[]>;

@Injectable({ providedIn: 'root' })
export class WithIdStringDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/with-id-string-details';

  constructor(protected http: HttpClient) {}

  create(withIdStringDetails: IWithIdStringDetails): Observable<EntityResponseType> {
    return this.http.post<IWithIdStringDetails>(this.resourceUrl, withIdStringDetails, { observe: 'response' });
  }

  update(withIdStringDetails: IWithIdStringDetails): Observable<EntityResponseType> {
    return this.http.put<IWithIdStringDetails>(this.resourceUrl, withIdStringDetails, { observe: 'response' });
  }

  find(withIdStringId: string): Observable<EntityResponseType> {
    return this.http.get<IWithIdStringDetails>(`${this.resourceUrl}/${withIdStringId}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWithIdStringDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(withIdStringId: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${withIdStringId}`, { observe: 'response' });
  }
}
