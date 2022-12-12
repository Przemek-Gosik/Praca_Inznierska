import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NewLogin } from 'src/app/models/newLogin';
import { NewPassword } from 'src/app/models/newPassword';
import { User } from 'src/app/models/user';
import { UserRegistration } from 'src/app/models/userRegistration';
import { EditAccountService } from 'src/app/services/edit-account.service';
import { LocalstorageService } from 'src/app/services/localstorage.service';

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
  editPasswordFailed: boolean = false;
  errorLoginResponse: string = "";
  errorPasswordResponse: string = "";
  newLogin: Partial<NewLogin> = {};  
  newPassword: Partial<NewPassword> = {};

  constructor(
    private editService: EditAccountService,
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
    this.editLoginFailed=false
  }
  resetPasswordError(){
    this.editPasswordFailed=false
  }


}
