import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { forgotPassword } from '../models/forgot-password-model';
import { VerificationCode } from '../models/verification-code';

@Injectable({
  providedIn: 'root'
})
export class ForgotPasswordService {

  apiUrl = 'http://localhost:8080/api/auth/passwordRecovery/';
  
  constructor(private http: HttpClient) { }

  sendPasswordRecoveryCode(email: forgotPassword){
    return this.http.post(`${this.apiUrl}email`,email);
  }

  sendNewPassword(email: forgotPassword, code: VerificationCode){
    return this.http.patch(`${this.apiUrl}code/${email}`, code);
  }
  // sendNewPassword(code: VerificationCode){
  //   return this.http.patch(`${this.apiUrl}code/email`, code);
  // }


}
