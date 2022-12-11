import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { User } from '../models/user';
import { VerificationCode } from '../models/verification-code';
@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  apiUrl = 'http://localhost:8080/api/auth/';
  //deklaracja tokena

  constructor(private http: HttpClient) {
  }

  registerUser(user: User):Observable<any>{
    console.log(user);
    return this.http.post<any>(`${this.apiUrl}register`,user)
  }

  verifyUser(code: VerificationCode):Observable<Object>{
    console.log(code);
    return this.http.patch(`${this.apiUrl}confirmEmail`,code)
  }


}
