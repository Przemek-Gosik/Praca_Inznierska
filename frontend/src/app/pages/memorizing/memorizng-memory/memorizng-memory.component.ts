import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { ImageConsts } from "src/app/consts/image.consts";
import { TypeMemory } from "src/app/consts/type-consts";
import { CardDetails, MemorizngResult } from "src/app/models/memorizing-model";
import { LoginService } from "src/app/services/login.service";
import { MemorizingService } from "src/app/services/memorizing.service";
import { SettingsService } from "src/app/services/settings.service";
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
  time: number = 3000;
  constructor(
    private loginService: LoginService,
    private timerService: TimerService,
    private memorizingService: MemorizingService,
    private router: Router,
    private route: ActivatedRoute,
    protected settingService: SettingsService
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
      this.time=3000;
      break;
      case("MEDIUM"):
      amountOfCards = 6
      this.time=6000
      break;
      case("ADVANCED"):
      amountOfCards = 12
      this.time=10000
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
      },this.time)
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
    this.cards = []
    this.getCards()
    this.showCards()
  }

  goBack():void{
    this.reset()
    this.router.navigate(['/courses/memorizing/',{
      type: TypeMemory.MEMORY
    }])
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
