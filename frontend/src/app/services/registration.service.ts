import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { User } from '../models/user';
import { VerificationCode } from '../models/verification-code';
import { LocalstorageService } from './localstorage.service';
import { TokenService } from './token.service';
@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  apiUrl = 'http://localhost:8080/api/auth/';


  constructor(private http: HttpClient,private tokenService : TokenService) {
  }

  registerUser(user: User):Observable<any>{
    return this.http.post<any>(`${this.apiUrl}register`,user)
  }

  checkIfEmailIsTaken(email:String):Observable<Object>{
    return this.http.get(`${this.apiUrl}emailIsTaken/${email}`);
  }

  chechIfLoginIsTaken(login:String):Observable<Object>{
    return this.http.get(`${this.apiUrl}loginIsTaken/${login}`);
  }

  verifyUser(code: VerificationCode):Observable<Object>{
    return this.http.patch(`${this.apiUrl}confirmEmail`, code , 
      {headers: this.tokenService.getHeaderWithToken() });
  }
}
