import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { User } from 'src/app/common/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  @Output() isSelectedUserChange = new EventEmitter<boolean>();

  users!: User[];

  selectedUser: User | undefined;

  isSelectedUser: boolean = false;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
    const userJSON = localStorage.getItem('user');
    if (userJSON !== null) {
      this.selectedUser = JSON.parse(userJSON);
    }
  }

  loadUsers(): void {
    this.userService.getUsers().subscribe(
      data => this.users = data
    );
  }

  onSelectUser() {
    const userJSON = JSON.stringify(this.selectedUser);
    localStorage.setItem('user', userJSON);
    this.isSelectedUser = true;
    this.isSelectedUserChange.emit(this.isSelectedUser);
  }



}
