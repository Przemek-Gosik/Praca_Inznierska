import { Component, OnInit } from '@angular/core';
import { Report } from 'src/app/models/report';
import { LoginService } from 'src/app/services/login.service';
import { ReportService } from 'src/app/services/report.service';
import { TimerService } from 'src/app/services/timer.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {
  
  report:Report = {
    email: '',
    date: '',
    text: '',
    title: ''
  }
  
  constructor(
    private loginService: LoginService,
    private timerService: TimerService,
    private reportService: ReportService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  sendReport():void{
    this.report.date = this.timerService.getCurrentDate()
    console.log(this.report)
    if(this.loginService.loggedInUser()){
      this.reportService.createUserReport(this.report).subscribe(()=>{
        this.router.navigate(["/"])
      })

    }else{
      this.reportService.createReport(this.report).subscribe(()=>{
        console.log("erer")
      })
    }
  }

  
}
