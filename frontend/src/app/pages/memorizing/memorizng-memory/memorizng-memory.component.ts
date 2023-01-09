import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { ImageConsts } from "src/app/consts/image.consts";
import { CardDetails, MemorizngResult } from "src/app/models/memorizing-model";
import { LoginService } from "src/app/services/login.service";
import { MemorizingService } from "src/app/services/memorizing.service";
import { TimerService } from "src/app/services/timer.service";

@Component({
  selector: "app-memorizng-memory",
  templateUrl: "./memorizng-memory.component.html",
  styleUrls: ["./memorizng-memory.component.css"],
})
export class MemorizngMemoryComponent implements OnInit {
  cards: CardDetails[] = [];
  chosenCards: CardDetails[] = [];
  points: number = 0;
  matchedCards: number = 0;
  dateTime: string = "";
  saved: boolean = false;
  level: string = "EASY";
  finished : boolean = false;
  constructor(
    private loginService: LoginService,
    private timerService: TimerService,
    private memorizingService: MemorizingService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.dateTime = this.timerService.getCurrentDate()
    this.route.paramMap.subscribe(param=>{
      let levelPom = param.get('level')
      if(levelPom){
        this.level = levelPom
        this.getCards()
        this.showCards()
      }
    })
  }

  getCards():void{
    let amountOfCards: number = 0
    switch(this.level){
      case("EASY"):
      amountOfCards = 3
      break;
      case("MEDIUM"):
      amountOfCards = 6
      break;
      case("ADVANCED"):
      amountOfCards = 12
      break;
    }
    let cardNames:string[] = ImageConsts.IMAGE_NAMES.slice(0,amountOfCards)
    cardNames.forEach((name)=>{
      let card: CardDetails = {
        imageName: name,
        state: "chosen"
      };
      
      this.cards.push({...card})
      this.cards.push({...card})
    })
    for(let i = this.cards.length -1;i>0;i--){
      const j:number = Math.floor(Math.random()*(i+1));

      [this.cards[i],this.cards[j]] = [this.cards[j],this.cards[i]];
    }
  }

  showCards():void{
    this.cards.forEach((card)=>{
      setTimeout(()=>{
        card.state = 'none'
      },4000)
    })
  }

  loggedIn():boolean{
    return this.loginService.loggedInUser();
  }

  cardClicked(index: number): void {
    let card: CardDetails = this.cards[index];
    if (card.state == "none" && this.chosenCards.length < 2) {
      card.state = "chosen";
      this.chosenCards.push(card);
      if (this.chosenCards.length == 2) {
        this.checkCards();
      }
    }
  }

  checkCards(): void {
    setTimeout(() => {
      let cardOne: CardDetails = this.chosenCards[0];
      let cardTwo: CardDetails = this.chosenCards[1];
      if(cardOne.imageName == cardTwo.imageName){
        cardOne.state = 'matched'
        cardTwo.state = 'matched'
        this.points += 1
        this.matchedCards += 1
        const MAX_MATCHES: number = this.cards.length/2
        if(this.matchedCards === MAX_MATCHES){
          this.finished = true
        }
      }else{
        this.points -= 1
        cardOne.state = 'none'
        cardTwo.state = 'none'
      }
      this.chosenCards = []
    }, 1000);
  }

  reset(): void {
    this.points = 0;
    this.chosenCards = [];
  }

  goBack():void{
    this.reset()
    this.router.navigate(['/memorizing'])
  }

  saveResult(): void {
    let result : MemorizngResult = {
      level: this.level,
      score: this.points,
      startTime: this.dateTime,
      type: "MEMORY"
    }
    this.memorizingService.saveResult(result).subscribe((res:any)=>{
      console.log(res)
    })
    this.saved = true
  }
}
