import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { ButtonNames } from "src/app/consts/button-names-consts";
import { MemorizingNumbers, MemorizngResult } from "src/app/models/memorizing-model";
import { GameService } from "src/app/services/game.service";
import { LoginService } from "src/app/services/login.service";
import { MemorizingService } from "src/app/services/memorizing.service";
import { TimerService } from "src/app/services/timer.service";
import {  CdkDragDrop,moveItemInArray} from '@angular/cdk/drag-drop'
import { TypeMemory } from "src/app/consts/type-consts";
import { MatDialog } from "@angular/material/dialog";
import { MemorizngResultComponent } from "../memorizng-result/memorizng-result.component";
import { LevelConsts } from "src/app/consts/level-consts";

@Component({
  selector: "app-memorizng-mnemonics",
  templateUrl: "./memorizng-mnemonics.component.html",
  styleUrls: ["./memorizng-mnemonics.component.css"],
})
export class MemorizngMnemonicsComponent implements OnInit{
  title: string = "Gra na zapamiętywanie";
  level: string = "EASY";
  points: number = 0;
  isPresented: boolean = true;
  numbers: MemorizingNumbers={
    randomNumbers:[],
    shuffledRandomNumbers:[]
  }; 
  index: number = 0;
  dateTime: string = "";
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private loginService: LoginService,
    private timerService: TimerService,
    private memorizingService: MemorizingService,
    private dialog: MatDialog
    
  ) {}

  ngOnInit(): void {
    this.dateTime=this.timerService.getCurrentDate();
    this.route.paramMap.subscribe((param) => {
      let levelPom = param.get("level");
      if (levelPom) {
        this.level = levelPom;
        this.memorizingService.generateRandomNumbers(this.level).subscribe((res:MemorizingNumbers)=>{
          console.log(res)
          this.numbers = res
        })
      }
    });
  }

  showNumber():number{
    return this.numbers.randomNumbers[this.index];
  }

  incrementIndex(){
    if(this.index < this.numbers.randomNumbers.length-1){
    this.index += 1
    }else{
      this.isPresented = false
    }
  }

  reset(): void {
    this.points = 0
    this.index = 0
    this.isPresented=true
  }
 
  
  calculatePoints(): void {
    var points: number = 0
    for(let i = 0;i<this.numbers.randomNumbers.length;i++){
      if(this.numbers.randomNumbers[i]==this.numbers.shuffledRandomNumbers[i]){
        points +=1
      }
    }
    let result: MemorizngResult={
      level: this.level,
      score: points,
      startTime: this.dateTime,
      type: TypeMemory.MNEMONICS
    }

    this.openDialog(result,this.getMaxPoints());
  }

  getMaxPoints():number{
    return this.numbers.randomNumbers.length
  }

  openDialog(result:MemorizngResult,maxPoints:number):void{
    this.dialog.open(MemorizngResultComponent,{
      width: '500px',
      height: '00px',
      data:{
        result: result,
        maxPoints: maxPoints
      }
    }).afterClosed().subscribe(()=>{
      this.goBack();
    })
  }

  goBack(): void {
    this.reset()
    this.router.navigate(["/memorizing"]);
  }

  loggedIn():boolean{
    return this.loginService.loggedInUser()
  }

  drop(event: CdkDragDrop<number[]>){
    moveItemInArray(this.numbers.shuffledRandomNumbers,event.previousIndex,event.currentIndex);
  }
  
  
}
