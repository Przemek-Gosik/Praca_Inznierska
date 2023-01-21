import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { timer } from 'rxjs';
import { ButtonNames } from 'src/app/consts/button-names-consts';
import { LevelConsts } from 'src/app/consts/level-consts';
import { TypeReading } from 'src/app/consts/type-consts';
import { FidningNumbers, ReadingResult, Table } from 'src/app/models/reading-model';
import { GameService } from 'src/app/services/game.service';
import { LoginService } from 'src/app/services/login.service';
import { ReadingService } from 'src/app/services/reading.service';
import { TimerService } from 'src/app/services/timer.service';

@Component({
  selector: 'app-finding-numbers',
  templateUrl: './finding-numbers.component.html',
  styleUrls: ['./finding-numbers.component.css']
})
export class FindingNumbersComponent implements OnInit,GameService {
  level: string = LevelConsts.LEVEL_SELECT[0].value
  gameNumbers: FidningNumbers = {
    numbersToFind: [],
    schultzTables: []
  }
  table: Table[] = []
  index: number = 0
  points : number = 0
  elapsedTime: number = 0
  dateTime: string = ""
  size: number = 10
  tableSize: number = 9
  rowSize: number = 3
  saved: boolean = false
  hidden: boolean = true
  buttonActionName: string = ButtonNames.START_NAME
  finished: boolean = false

  constructor(private route: ActivatedRoute,
    private router: Router,
    public timerService: TimerService,
    private loginService: LoginService,
    private readingService: ReadingService) { }

  ngOnInit(): void {
    this.dateTime = this.timerService.getCurrentDate()
    this.route.paramMap.subscribe(param=>{
      let levelPom = param.get('level')
      if(levelPom){
        this.level = levelPom
      }
      console.log(this.level)
      this.readingService.getNumbersForFindingGame(this.level).subscribe((res:FidningNumbers)=>{
        this.gameNumbers = res
        this.size = this.gameNumbers.numbersToFind.length
        this.tableSize = this.gameNumbers.schultzTables[0].length
        this.rowSize = Math.sqrt(this.tableSize)
        this.createTable()
      })
    })

  }

  createTable() :void{
    this.table = []
    for (let i = 0; i < this.tableSize; i += this.rowSize) {
      let pom: number[] = this.gameNumbers.schultzTables[this.index].slice(i, this.rowSize + i);
      let rowPom: Table = {
        column: pom,
      };
      this.table.push(rowPom);
    }
  }

  reset(): void {
    window.location.reload()
  }

  startOrPause(): void {
    if(this.buttonActionName == ButtonNames.START_NAME){
      this.hidden = false
      this.timerService.startTimer()
      this.buttonActionName = ButtonNames.PAUSE_NAME
    }else{
      this.hidden = true
      this.timerService.stopTimer()
      this.hidden = true
      this.buttonActionName = ButtonNames.START_NAME
    }
  }

  calculatePoints(): void {
    this.startOrPause()
    this.elapsedTime = this.timerService.calculateTime()
    this.finished = true
  }

  goBack(): void {
    this.timerService.clearTimer()
    this.router.navigate(['/courses/reading'])
  }

  getNumberToFind():number{
    return this.gameNumbers.numbersToFind[this.index]
  }

  

  checkPoints(num: number):void{
    if(num == this.gameNumbers.numbersToFind[this.index]){
      this.points += 1
      this.index += 1
      if(this.index == this.size){
        this.calculatePoints()
      }else{
        this.createTable()
      }
      
    }else{
      this.points -= 1
    }
  }

  loggedIn():boolean{
    return this.loginService.loggedInUser()
  }

  saveResult():void{
    this.saved = true
    let result:ReadingResult = {
      level: this.level,
      score: this.points,
      startTime: this.dateTime,
      time: this.elapsedTime,
      type: TypeReading.FINDING_NUMBERS
    }
    console.log(result)
    this.readingService.saveResult(result).subscribe((res:any)=>{
      console.log(res)
    })
  }
}
