import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LocalstorageService } from 'src/app/services/localstorage.service';
import { LoginService } from 'src/app/services/login.service';
import { ThemeService } from 'src/app/services/theme.service';
import { AccountComponent } from '../account.component';

export interface DialogData {
  animal: string;
  name: string;
}

@Component({
  selector: 'app-delete-user-dialog',
  templateUrl: './delete-user-dialog.component.html',
  styleUrls: ['./delete-user-dialog.component.css']
})
export class DeleteUserDialogComponent implements OnInit {

  errorResponse: string = "";
  deteleUserFailed : boolean = false;

  constructor(    
    public dialogRef: MatDialogRef<DeleteUserDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private loginService: LoginService,
    private localStorageService: LocalstorageService,
    protected themeService: ThemeService,
    private router: Router) { }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  deleteUser(){
    this.loginService.deleteUser().subscribe((res:any)=>{
       this.localStorageService.removeItemFromStorage('token');
       this.localStorageService.removeItemFromStorage('user');
       this.localStorageService.removeItemFromStorage('setting');
       this.logoutUser();
       //alert("Twoje konto zostało usunięte.");
       this.dialogRef.close();
    }, err => {
      this.deteleUserFailed = true
      this.errorResponse = err.error.message
    })
  }

  logoutUser(){
    this.loginService.logoutUser();
    this.router.navigate(['/account']);
  }
}
