import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from '../../app.constants';
import { createRequestOption } from '../../core/request/request-util';
import { IWithIdString } from '../../shared/model/with-id-string.model';

type EntityResponseType = HttpResponse<IWithIdString>;
type EntityArrayResponseType = HttpResponse<IWithIdString[]>;

@Injectable({ providedIn: 'root' })
export class WithIdStringService {
  public resourceUrl = SERVER_API_URL + 'api/with-id-strings';

  constructor(protected http: HttpClient) {}

  create(withIdString: IWithIdString): Observable<EntityResponseType> {
    return this.http.post<IWithIdString>(this.resourceUrl, withIdString, { observe: 'response' });
  }

  update(withIdString: IWithIdString): Observable<EntityResponseType> {
    return this.http.put<IWithIdString>(this.resourceUrl, withIdString, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IWithIdString>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWithIdString[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
