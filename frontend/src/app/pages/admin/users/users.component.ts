import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { RoleConsts } from 'src/app/consts/role-consts';
import { Role, User } from 'src/app/models/user';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: User[] = [];
  dataSource = new MatTableDataSource<User>();
  @ViewChild(MatPaginator) paginator?: MatPaginator;
  displayedColumns: string[] = ['number','login','email','id','action1','action2'];


  constructor(
    private usersService: UsersService) { }

  ngOnInit(): void {
    this.getAllUsers()
  }

  getAllUsers(){
    this.usersService.getAllUsers().subscribe((res:User[])=>{
    this.users = res
    this.dataSource.data = this.users
    this.dataSource.paginator = this.paginator!
    })
  }

  takeAdminRole(user: User){
    this.usersService.takeAdminRole(user.idUser).subscribe((res:User)=>{
      let index = this.users.findIndex(obj => obj.idUser == user.idUser)
      this.users[index] = res
      this.dataSource.data = this.users
    })
  }
  giveAdminRole(user: User){
    this.usersService.giveAdminRole(user.idUser).subscribe((res:User)=>{
      let index = this.users.findIndex(obj => obj.idUser == user.idUser)
      this.users[index] = res
      this.dataSource.data = this.users
    })
  }

  isUserAdmin(user:User):boolean{
    let isAdmin: boolean = false
    for(var role of user.roles){
      if(role.roleName == RoleConsts.ADMIN){
        isAdmin = true
      }
    }
    return isAdmin
  }

  deleteUser(user:User){
    this.usersService.deleteUser(user.idUser).subscribe(()=>{
      let index = this.users.findIndex(obj => obj.idUser == user.idUser)
      this.users.splice(index,1)
      this.dataSource.data=this.users
    })
  }

}
