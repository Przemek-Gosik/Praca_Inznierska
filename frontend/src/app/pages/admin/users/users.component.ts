import { Component, OnInit } from '@angular/core';
import { RoleConsts } from 'src/app/consts/role-consts';
import { Role, User } from 'src/app/models/user';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: User[] = []
  constructor(
    private usersService: UsersService) { }

  ngOnInit(): void {
    this.getAllUsers()
  }

  getAllUsers(){
    this.usersService.getAllUsers().subscribe((res:User[])=>{
    this.users = res
    })
  }

  takeAdminRole(index: number){
    let user:User = this.users[index]
    this.usersService.takeAdminRole(user.idUser)
  }
  giveAdminRole(index:number){
    let user:User = this.users[index]
    this.usersService.giveAdminRole(user.idUser)
  }

  isUserAdmin(index:number):boolean{
    let user:User = this.users[index]
    let isAdmin: boolean = false
    console.log(user.roles)
    for(var role of user.roles){
      if(role.roleName == RoleConsts.ADMIN){
        isAdmin = true
      }
    }
    return isAdmin
  }

  deleteUser(index:number){
    let user: User = this.users[index]
    this.usersService.deleteUser(user.idUser)
  }

}
