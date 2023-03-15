import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Report } from 'src/app/models/report';
import { LocalstorageService } from 'src/app/services/localstorage.service';
import { LoginService } from 'src/app/services/login.service';
import { ReportService } from 'src/app/services/report.service';
import { TimerService } from 'src/app/services/timer.service';

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
    private reportService: ReportService
  ) { }

  ngOnInit(): void {
  }

  sendReport():void{
    this.report.date = this.timerService.getCurrentDate()
    console.log(this.report)
    if(this.loginService.loggedInUser()){
      this.reportService.createUserReport(this.report).subscribe(()=>{
        console.log("rrr")
      })
    }else{
      this.reportService.createReport(this.report).subscribe(()=>{
        console.log("erer")
      })
    }
  }

  
}
