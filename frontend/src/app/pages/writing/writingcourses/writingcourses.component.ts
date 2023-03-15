import { Component, ModuleWithComponentFactories, OnInit } from '@angular/core';
import { Module } from 'src/app/models/writing-model';
import { LoginService } from 'src/app/services/login.service';
import { WritingService } from 'src/app/services/writing.service';
@Component({
  selector: 'app-writingcourses',
  templateUrl: './writingcourses.component.html',
  styleUrls: ['./writingcourses.component.css']
})
export class WritingcoursesComponent implements OnInit {
  title: string = "Wybór dostępnych lekcji";
  modules: Module[]=[]; 
  regularDistribution = 100 / 3;
  constructor(private writingService:WritingService,private loginService:LoginService) { }

  ngOnInit(): void {
    if(this.loginService.loggedInUser()){
      this.writingService.getAllUserModules().subscribe((modules)=>{
        this.modules=modules
        console.log(this.modules)
      })
    }else{
      this.writingService.getAllModules().subscribe((modules)=>{
        this.modules=modules
        console.log(this.modules)
      })
    }
      
  }

  
}
