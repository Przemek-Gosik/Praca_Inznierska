import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { interval } from 'rxjs';
import { timeInterval, TimeInterval } from 'rxjs/internal/operators/timeInterval';
import { Lesson, Result } from 'src/app/models/writing-model';
import { WritingService } from 'src/app/services/writing.service';
import { SplitPipe } from '../../pipe/splitpipe';
import { WritingResultDialogComponent } from './writing-result-dialog/writing-result-dialog.component';
@Component({
  selector: 'app-writinglesson',
  templateUrl: './writinglesson.component.html',
  styleUrls: ['./writinglesson.component.css']
})
export class WritinglessonComponent implements OnInit {
  id : number = 0
  typedTexts : string[] = []
  width: number = 100
  timeElapsed: number = 0
  lesson : Lesson = {
    idFastWritingLesson: 0,
    idFastWritingCourse: 0,
    name: "",
    score: 0,
    number: 0,
    generatedCharacters: "",
    text: []
  }
  result : Result = {
    idFastWritingCourse: 0,
    startTime: 0,
    score: 0,
    typedLetters: 0
  }
  startName : string = "Start"
  pauzeName: string ="Pauza" 
  interval : number = 0;
  hundredsOfSecond : number = 0
  seconds : number= 0 
  minutes : number = 0 
  buttonActionName : string = this.startName
  blockLesson : boolean = true
  done: boolean = false
  dateTime: string = ""

  constructor(private router:Router,
    private route: ActivatedRoute,
    private writingService: WritingService,
    public dialog: MatDialog ) { }

  ngOnInit(): void {
    this.getLessonTime()
    this.route.paramMap.subscribe(param=>{
      let idPom = param.get('id')
      if(idPom){
        this.id=parseInt(idPom)
        this.getLessonById(this.id)
      }
    })
  }

  getLessonTime() : void {
    var date = new Date()
    this.dateTime = date.toISOString()
  }

  getLessonById(id: number) {
    this.writingService.getLessonById(id).subscribe((res)=>{
      this.lesson = res
      console.log(this.lesson.name)
    })
  }

 
  moveOn(i : number){
    let str : string = i.toString()
    document.getElementById(str)?.focus()
  }

  startOrPauze(){
    if(this.buttonActionName == this.startName){
      this.buttonActionName = this.pauzeName
      this.blockLesson = false
      this.startTimer()
      if(this.typedTexts.length > 0 ){
        console.log("tutaj")
        this.moveOn(this.typedTexts.length-1)
      }else{
        this.moveOn(0)
      }
    }else{
      this.buttonActionName = this.startName
      this.stopTimer()
      this.blockLesson = true
    }
  }

  startTimer(){
    this.interval = window.setInterval(()=> {
      this.hundredsOfSecond+=1
      if(this.hundredsOfSecond == 100){
        this.seconds +=1
        this.hundredsOfSecond = 0
      }
      if(this.seconds == 60){
        this.minutes +=1
        this.seconds = 0
      }
    },10)
  }

  displayTime():string{
    var timeDisplay = ""
    timeDisplay += this.addUnits(this.minutes)
    timeDisplay +=":"
    timeDisplay += this.addUnits(this.seconds)
    timeDisplay +=":"
    timeDisplay +=this.addUnits(this.hundredsOfSecond)
    return timeDisplay
  }

  addUnits(unit: number):string{
    var time :string = ""
    if(unit < 10){
      time += "0"
    }
    time += unit.toString()
    return time
  }

  stopTimer(){
    clearInterval(this.interval)
  }


  calculateTime(){
    this.timeElapsed = this.minutes *60
    this.timeElapsed += this.seconds
    this.timeElapsed += this.hundredsOfSecond/100
  }

  reset(){
    window.location.reload()
  }

  openDialog(score: number, typedLetters : number
  ):void {
    console.log(score)
    this.dialog.open(WritingResultDialogComponent, {
      width: '1000px',
      height: '1000px'
  }).afterClosed().subscribe(() =>{
    this.router.navigate(["/writing/course"])
  })
  }

  setTextColor(i:number,j:number):string{
    if(this.typedTexts.length < i+1){
      return "black"
    }else{
    if(j+1>this.typedTexts[i].length){
        return "black"
    }else{
      var char : string = this.typedTexts[i].charAt(j)
      if(char == this.lesson.text[i].charAt(j)){
        return "green"
      }else{
        return "red"
      }
    }
  }
  }

  calculatePoints(){
    let points : number = 0
    let typedLettes : number = 0
    let texts : string[] = this.lesson.text
    for(let i = 0 ;i<this.typedTexts.length ; i++){
      let textPom :string = this.typedTexts[i]
        for( let j = 0 ; j<textPom.length ;j++){
              if(textPom.charAt(j) === texts[i].charAt(j)){
                points +=1
                typedLettes +=1
              }
        }
        
    }
    this.calculateTime()
    console.log(points)
    console.log(this.timeElapsed)
    this.lesson.score=points
    this.openDialog(points,typedLettes)
    this.done = true
  }

}
