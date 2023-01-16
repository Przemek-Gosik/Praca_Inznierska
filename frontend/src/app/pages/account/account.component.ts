import { Token } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { LocalstorageService } from 'src/app/services/localstorage.service'
import { LoginService } from 'src/app/services/login.service';
import { WritingTextResultDialogComponent } from '../writing/writingtexts/writing-test/writing-text-result-dialog/writing-text-result-dialog.component';
import { DeleteUserDialogComponent } from './delete-user-dialog/delete-user-dialog.component';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  userDelete: Partial<User> = {}; 
  errorResponse: string = "";
  deteleUserFailed : boolean = false;
  // result: string ="";

  animal: string="";
  name: string="";

  constructor(
    private localStorageService: LocalstorageService,
    private router: Router,
    private loginService: LoginService,
    private authenticationService: AuthenticationService,
    public dialog: MatDialog,
    ) 
    { }

  ngOnInit(): void {
  }

  logoutUser(){
    this.loginService.logoutUser();
    this.router.navigate(['/account']);
  }

  loggedInUser(){
    return this.loginService.loggedInUser();
  }

  isUserAdmin(){
    return this.authenticationService.isUserAdmin()
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DeleteUserDialogComponent, {
      width:'400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
