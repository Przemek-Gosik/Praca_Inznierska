import { Component, OnInit } from '@angular/core';
import { forgotPassword } from 'src/app/models/forgot-password-model';
import { VerificationCode } from 'src/app/models/verification-code';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  constructor() { }

  forgotPassword: Partial<forgotPassword> = {};
  verificationCode: Partial<VerificationCode> = {};
  title = "Przypomnienie hasła"

  ngOnInit() {
  }
}
