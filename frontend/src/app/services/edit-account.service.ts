import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NewEmail } from '../models/newEmail';
import { NewLogin } from '../models/newLogin';
import { NewPassword } from '../models/newPassword';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class EditAccountService {

  apiUrl = 'http://localhost:8080/api/auth/';

  constructor(private http: HttpClient,private tokenService : TokenService) { }

  editLogin(newLogin: NewLogin):Observable<any>{
    return this.http.patch<any>(`${this.apiUrl}changeLogin`,newLogin,{headers: this.tokenService.getHeaderWithToken() })
  }

  editEmail(email: NewEmail):Observable<any>{
    return this.http.patch<any>(`${this.apiUrl}changeEmail`, email,{headers: this.tokenService.getHeaderWithToken() })
  }

  editPassword(newPassword: NewPassword):Observable<any>{
    return this.http.patch<any>(`${this.apiUrl}changePassword`,newPassword,{headers: this.tokenService.getHeaderWithToken() })
  }
}
