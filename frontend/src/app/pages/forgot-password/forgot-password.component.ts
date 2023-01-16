import { Component, OnInit } from '@angular/core';
import { forgotPassword } from 'src/app/models/forgot-password-model';
import { VerificationCode } from 'src/app/models/verification-code';
import { ForgotPasswordService } from 'src/app/services/forgot-password.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  constructor(private forgotPasswordService: ForgotPasswordService) { }

  forgotPassword: Partial<forgotPassword> = {};
  verificationCode: Partial<VerificationCode> = {};
  title = "Przypomnienie hasła";
  errorResponse: string = "";
  verificationFailed: boolean = false;
  forgotPasswordFailed: boolean = false;
  newPassword: any = "";
  email: any = "";
  newPass: string = "";

  ngOnInit() {
  }

  sendPasswordRecoveryCode(){
    this.forgotPasswordService.sendPasswordRecoveryCode(this.forgotPassword as forgotPassword).subscribe((res:any) => {
      this.email = this.forgotPassword.email;
    })
  }

  sendNewPassword(){
    this.forgotPasswordService.sendNewPassword(this.email as forgotPassword, this.verificationCode as VerificationCode).subscribe((res:any)=> {
      this.newPassword = res;
      // this.newPass = JSON.stringify(this.newPassword);
      this.newPass = this.newPassword.value;
      console.log(this.newPassword);
    })
  }


}
