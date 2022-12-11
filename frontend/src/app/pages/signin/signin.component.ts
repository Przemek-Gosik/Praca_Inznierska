import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserLogin } from 'src/app/models/userLogin';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  title: string = "Logowanie";
  hide: boolean = true;

  user: Partial<UserLogin> = {};

  clickedicon="";
  // clickedlabel: string =' ';


 constructor(
  private httpLogin: LoginService,
  private route: Router
  ) { }

  ngOnInit(): void {
  }


  userLogin(){
    console.log(this.user);
    this.httpLogin.loginUser(this.user as UserLogin).subscribe((res:any) => {
      console.log('res',res)
      localStorage.setItem('token',res.token);
      this.route.navigateByUrl('/');
    })
  }

  clickedIcon(){
    this.clickedicon="active";
  }

  
}
