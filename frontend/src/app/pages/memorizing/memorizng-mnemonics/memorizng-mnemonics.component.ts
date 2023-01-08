import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { ButtonNames } from "src/app/consts/button-names-consts";
import { GameService } from "src/app/services/game.service";
import { LoginService } from "src/app/services/login.service";
import { TimerService } from "src/app/services/timer.service";

@Component({
  selector: "app-memorizng-mnemonics",
  templateUrl: "./memorizng-mnemonics.component.html",
  styleUrls: ["./memorizng-mnemonics.component.css"],
})
export class MemorizngMnemonicsComponent implements OnInit, GameService {
  title: string = "Gra na zapamiętywanie";
  level: string = "EASY";
  points: number = 0;
  saved: boolean = false;
  buttonActionName: string = ButtonNames.START_NAME;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private loginService: LoginService,
    public timerService: TimerService
  ) {}

  reset(): void {
    this.points = 0

  }
  startOrPause(): void {
    if(this.buttonActionName == ButtonNames.START_NAME){
      this.buttonActionName = ButtonNames.PAUSE_NAME
      this.timerService.startTimer()
    }else{
      this.buttonActionName = ButtonNames.START_NAME
    }
  }
  
  calculatePoints(): void {
    throw new Error("Method not implemented.");
  }

  goBack(): void {
    this.timerService.stopTimer()
    this.buttonActionName = ButtonNames.START_NAME
    this.router.navigate(["/memorizng"]);
  }

  loggedIn():boolean{
    return this.loginService.loggedInUser()
  }
  
  ngOnInit(): void {
    this.route.paramMap.subscribe((param) => {
      let levelPom = param.get("level");
      if (levelPom) {
        this.level = levelPom;
      }
    });
  }
}
