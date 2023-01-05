import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NewEmail } from 'src/app/models/newEmail';
import { NewLogin } from 'src/app/models/newLogin';
import { NewPassword } from 'src/app/models/newPassword';
import { User } from 'src/app/models/user';
import { UserRegistration } from 'src/app/models/userRegistration';
import { EditAccountService } from 'src/app/services/edit-account.service';
import { LocalstorageService } from 'src/app/services/localstorage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-account',
  templateUrl: './edit-account.component.html',
  styleUrls: ['./edit-account.component.css']
})
export class EditAccountComponent implements OnInit {

  title="Edycja danych"
  userEdit: Partial<UserRegistration> = {};  
  hideOld: boolean = true;
  hideNew: boolean = true;
  changeLoginForm = NgForm;
  changeEmailForm = NgForm;
  changePasswordForm = NgForm;
  editLoginFailed: boolean = false;
  editEmailFailed: boolean = false;
  editPasswordFailed: boolean = false;
  errorLoginResponse: string = "";
  errorEmailResponse: string = "";
  errorPasswordResponse: string = "";
  newLogin: Partial<NewLogin> = {};  
  newEmail: Partial<NewEmail> = {};
  newPassword: Partial<NewPassword> = {};

  constructor(
    private editService: EditAccountService,
    private router: Router,
    private localStorageService: LocalstorageService) { }

  ngOnInit(): void {
  }

  editLogin(){
    console.log(this.newLogin);
    this.editService.editLogin(this.newLogin as NewLogin).subscribe((res:any)=>{
          this.localStorageService.setItemToStorage('token',res.token);
          this.localStorageService.setItemToStorage('user',res.userDto);
          this.localStorageService.setItemToStorage('setting',res.settingDto);
    },err =>{
      this.editLoginFailed = true
      this.errorLoginResponse = err.error.message
    })
  }

  editEmail(){
      console.log(this.newEmail)
      console.log("zmiana email")
       this.editService.editEmail(this.newEmail as NewEmail).subscribe((res:any)=>{
            // this.localStorageService.setItemToStorage('token',res.token);
            // this.localStorageService.setItemToStorage('user',res.userDto);
            // this.localStorageService.setItemToStorage('setting',res.settingDto);
          this.router.navigate(['/account/signin/signup/verification']);
        },err =>{
          this.editEmailFailed = true
          this.errorEmailResponse = err.error.message
        })
  }

  editPassword(){
    console.log(this.newPassword);
    this.editService.editPassword(this.newPassword as NewPassword).subscribe((res:any)=>{
    },err =>{
      this.editPasswordFailed = true
      this.errorPasswordResponse = err.error.message
    })
  }

  resetLoginError(){
    this.editLoginFailed=false;
  }

  resetEmailError(){
    this.editEmailFailed=false;
  }

  resetPasswordError(){
    this.editPasswordFailed=false;
  }


}
