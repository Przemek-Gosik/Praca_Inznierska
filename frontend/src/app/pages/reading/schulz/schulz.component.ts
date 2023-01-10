import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { ButtonNames } from "src/app/consts/button-names-consts";
import { LevelConsts } from "src/app/consts/level-consts";
import { TypeReading } from "src/app/consts/type-consts";
import { ReadingResult, Table } from "src/app/models/reading-model";
import { GameService } from "src/app/services/game.service";
import { LoginService } from "src/app/services/login.service";
import { ReadingService } from "src/app/services/reading.service";
import { TimerService } from "src/app/services/timer.service";
import { WritingService } from "src/app/services/writing.service";

@Component({
  selector: "app-schulz",
  templateUrl: "./schulz.component.html",
  styleUrls: ["./schulz.component.css"],
})
export class SchulzComponent implements OnInit, GameService {
  level: string  = LevelConsts.LEVEL_SELECT[0].value
  numbers: number[] = [];
  tableRows: Table[] = [];
  currentNumber: number = 1;
  points: number = 0;
  size: number = 0;
  elapsedTime: number = 0
  dateTime: string = ""
  hidden: boolean = true
  saved: boolean = false
  done: boolean = false
  buttonActionName: string = ButtonNames.START_NAME

  constructor(
    private readingService: ReadingService,
    public timerService: TimerService,
    private loginService: LoginService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.timerService.getCurrentDate()
    this.route.paramMap.subscribe(param=>{
      var levelPom = param.get('level')
      if(levelPom){
        this.level = levelPom
      }
      this.readingService
        .getNumbersForSchubertTable(this.level)
        .subscribe((res: number[]) => {
        this.numbers = res;
        this.size = this.numbers.length;
        this.createTable();
      });
    
  })
  }

  reset(): void {
    this.points = 0
    this.timerService.clearTimer()
    this.buttonActionName = ButtonNames.START_NAME
    this.currentNumber = 1
    this.hidden = true
  }

  startOrPause(): void {
    if(this.buttonActionName == ButtonNames.START_NAME){
      this.hidden = false
      this.done = false
      this.timerService.startTimer()
      this.buttonActionName =  ButtonNames.PAUSE_NAME
    }else{
      this.buttonActionName = ButtonNames.START_NAME
      this.hidden = true
      this.timerService.stopTimer()
      this.done = true
    }
  }

  calculatePoints(): void {
    this.startOrPause()
    this.elapsedTime = this.timerService.calculateTime()
  }
  
  goBack(): void {
    this.timerService.stopTimer()
    this.router.navigate(["/reading/level",{
      type: TypeReading.SCHULTZ
    }])
  }

  createTable() :void{
    var columnSize: number = Math.sqrt(this.size);
    for (var i = 0; i < this.size; i += columnSize) {
      var pom: number[] = this.numbers.slice(i, columnSize + i);
      var columnPom: Table = {
        column: pom,
      };
      this.tableRows.push(columnPom);
    }
  }

  checkPoints(num: number) :void{
    if (num == this.currentNumber) {
      this.points += 1;
      this.currentNumber += 1;
      if(this.currentNumber == this.numbers.length+1){
        this.calculatePoints()
      }
    } else {
      this.points -= 1;
    }
  }

  checkColumn(i: number): boolean {
    var numberOfColumns = Math.sqrt(this.size);
    if (numberOfColumns % i == 0) {
      return true;
    } else {
      return false;
    }
  }

  loggedIn():boolean{
    return this.loginService.loggedInUser()
  }

  saveResult():void{
    this.saved = true
    let result:ReadingResult = {
      level: "",
      score: this.points,
      startTime: this.dateTime,
      time: 0,
      type: TypeReading.SCHULTZ
    }
  }
}
