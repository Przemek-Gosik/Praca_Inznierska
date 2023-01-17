import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { MemorizngResult } from 'src/app/models/memorizing-model';
import { LoginService } from 'src/app/services/login.service';
import { MemorizingService } from 'src/app/services/memorizing.service';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-memorizng-result',
  templateUrl: './memorizng-result.component.html',
  styleUrls: ['./memorizng-result.component.css']
})
export class MemorizngResultComponent implements OnInit {
  maxPoints: number = 0;
  result : MemorizngResult = {
    level: '',
    score: 0,
    startTime: '',
    type: ''
  }
  saved : boolean = false
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private memorizingService : MemorizingService, private loginService : LoginService, protected themeService: ThemeService)  { }

  ngOnInit(): void {
    this.result = this.data.result
    this.maxPoints = this.data.maxPoints
  }

  loggedIn():boolean{
    return this.loginService.loggedInUser();
  }

  saveResult():void{
    this.memorizingService.saveResult(this.result).subscribe((res:any)=>{
      console.log(res)
    })
    this.saved = true
  }

}
