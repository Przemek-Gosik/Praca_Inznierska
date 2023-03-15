import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { interval } from 'rxjs';
import { timeInterval, TimeInterval } from 'rxjs/internal/operators/timeInterval';
import { ButtonNames } from 'src/app/consts/button-names-consts';
import { Lesson, WritingLessonResult } from 'src/app/models/writing-model';
import { GameService } from 'src/app/services/game.service';
import { ThemeService } from 'src/app/services/theme.service';
import { TimerService } from 'src/app/services/timer.service';
import { WritingService } from 'src/app/services/writing.service';
import { SplitPipe } from '../../pipe/splitpipe';
import { WritingLessonResultDialogComponent } from './writing-lesson-result-dialog/writing-lesson-result-dialog.component';
@Component({
  selector: 'app-writinglesson',
  templateUrl: './writinglesson.component.html',
  styleUrls: ['./writinglesson.component.css']
})
export class WritinglessonComponent implements OnInit,GameService {

  @ViewChild("box") input!: ElementRef;

  id : number = 0
  typedTexts : string[] = []
  width: number = 170
  timeElapsed: number = 0
  numberOfTypedLetters: number = 0
  lesson : Lesson = {
    idWritingLesson: 0,
    idWritingLessonResult: 0,
    name: "",
    score: 0,
    number: 0,
    generatedCharacters: "",
    text: []
  }
  startName : string = "Start"
  pauzeName: string ="Pauza" 
  dateTime: string = ""
  buttonActionName : string = ButtonNames.START_NAME
  blockLesson : boolean = true
  done : boolean = false
  colorDay: string = "";
  colorNIGHT: string = "";

  theme: string = "";

  constructor(private router:Router,
    private route: ActivatedRoute,
    private writingService: WritingService,
    protected dialog: MatDialog,
    protected timerService: TimerService,
    private themeService: ThemeService) { }

  ngOnInit(): void {
    this.dateTime = this.timerService.getCurrentDate()
    this.timerService.clearTimer()
    this.route.paramMap.subscribe(param=>{
      let idPom = param.get('id')
      let idLastResult = param.get('lastResultId')
      if(idPom){
        this.id=parseInt(idPom)
        this.writingService.getLessonById(this.id).subscribe((res)=>{
        this.lesson = res
        if(idLastResult){
          this.lesson.idWritingLessonResult = parseInt(idLastResult)
        }
    })
      }  
    })
  }

  goBack(){
    this.timerService.stopTimer()
    this.router.navigate(["/courses/writing/course"])
  }

  getLessonById(id: number) {
    this.writingService.getLessonById(id).subscribe((res)=>{
      this.lesson = res
      console.log(this.lesson.name)
    })
  }

  moveOn(i : number){
    if(i >= this.lesson.text!.length){
      this.startOrPause()
    }else{
    let str : string = i.toString()
    document.getElementById(str)?.focus()
    }
  }

  startOrPause(){
    if(this.buttonActionName == ButtonNames.START_NAME){
      this.buttonActionName = ButtonNames.PAUSE_NAME
      this.blockLesson = false
      this.done = false
      this.timerService.startTimer()
      if(this.typedTexts.length > 0 ){
        this.input.nativeElement.focus();
      }else{
        this.input.nativeElement.focus();
      }
    }else{
      this.done = true
      this.buttonActionName = ButtonNames.START_NAME
      this.timerService.stopTimer()
      this.blockLesson = true
    }
  }

  reset(){
    this.timerService.clearTimer()
    this.buttonActionName = ButtonNames.START_NAME
    this.typedTexts = []
    this.done = false
    this.timeElapsed = 0
  }

  openDialog( 
  ):void {
    this.dialog.open(WritingLessonResultDialogComponent, {
      width: '500px',
      // height: '500px',
      data:{
        numberOfTypedLetters: this.numberOfTypedLetters,
        idResult: this.lesson.idWritingLessonResult,
        idLesson: this.lesson.idWritingLesson,
        score: this.lesson.score,
        time: this.timeElapsed,
        date: this.dateTime
      }
  })
  }

  getLetterColor(){
    this.theme=this.themeService.getTheme();
    if(this.theme=="NIGHT"){
      return "#ffffffb4"
    }
    else{
      return "black"
    }
  }

  getLetterInvalidColor(){
    this.theme=this.themeService.getTheme();
    if(this.theme=="NIGHT"){
      return "#b91e26"
    }
    else if (this.theme=="CONTRAST"){
      return "#070707"
    }
    else{
      return "red"
    }
  }

  getLetterValidColor(){
    this.theme=this.themeService.getTheme();
    if(this.theme=="NIGHT"){
      return "#076921"
    }
    else if (this.theme=="CONTRAST"){
      return "#070707"
    }
    else{
      return "green"
    }
  }

  setTextColor(i:number,j:number):string{
    if(this.typedTexts.length < i+1){
      return this.getLetterColor()
    }else{
      if(j+1>this.typedTexts[i].length){
        return this.getLetterColor()
      }else{
        var char : string = this.typedTexts[i].charAt(j)
        if(char == this.lesson.text![i].charAt(j)){
          return this.getLetterValidColor();
        }else{
          return this.getLetterInvalidColor();
        }
      }
    }
  }

  calculatePoints(){
    let points : number = 0
    let typedLetters : number = 0
    let texts : string[] = this.lesson.text!
    for(let i = 0 ;i<this.typedTexts.length ; i++){
      let textPom :string = this.typedTexts[i]
        for( let j = 0 ; j<textPom.length ;j++){
              if(textPom.charAt(j) === texts[i].charAt(j)){
                points +=1
              }
              typedLetters +=1
        } 
    }
    this.timeElapsed=this.timerService.calculateTime()
    this.lesson.score=points
    this.numberOfTypedLetters = typedLetters
    this.openDialog()
  }

  // calculateInputWidth(word :string){
  //   return 11*word.length
  // }
}
