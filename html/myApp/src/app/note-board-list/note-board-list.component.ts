import { Component, OnInit } from '@angular/core';
import { ToolService } from '../tool.service';
import { MatDialog } from '@angular/material/dialog';
import {
  NoteBoard,
  NoteBoardRequest,
  NoteBoardResponse,
} from '../models/note-board.interface';

@Component({
  selector: 'note-board-list',
  templateUrl: './note-board-list.component.html',
  styleUrls: ['./note-board-list.component.css'],
})
export class NoteBoardListComponent implements OnInit {
  constructor(
    private tool: ToolService,
    public dialog: MatDialog,
  ) {}

  title: string = '';
  content: string = '';
  pollingFrequency: number = 300000;
  noteBoardDataArray: NoteBoard[] = [];
  allComplete: boolean = false;
  displayedColumns: string[] = ['timestamp_without_timezone', 'title'];

  pollingData() {
    setTimeout(() => {}, this.pollingFrequency);
  }

  updateData(callback?: () => void) {
    this.tool.doPost(
      'getNoteBoard',
      {
        title: this.title,
        content: this.content,
      },
      (successResult: NoteBoardResponse) => {
        successResult.Data.forEach((newData: NoteBoard) => {
          newData.expanded = this.noteBoardDataArray.find(
            (currentData) => currentData.id === newData.id,
          )?.expanded;
          newData.checkToDelete = this.noteBoardDataArray.find(
            (currentData) => currentData.id === newData.id,
          )?.checkToDelete;
        });
        this.noteBoardDataArray = successResult.Data;

        if (callback) {
          callback();
        } else {
          this.pollingData();
        }
        console.log(successResult);
      },
      (failedResult: Error) => {
        this.pollingData();
        console.log(failedResult);
      },
    );
  }
  ngOnInit(): void {
    this.updateData();
    this.pollingData();
  }

  deleteNoteBoard() {
    this.tool.doPost<NoteBoardRequest, NoteBoardResponse>(
      'deleteNoteBoard',
      {
        idArray: this.noteBoardDataArray
          .map(
            (noteBoardData) => noteBoardData.checkToDelete && noteBoardData.id,
          )
          .filter((id) => !!id),
      },
      (successResult: NoteBoardResponse) => {
        this.updateData(() => {
          this.tool.openDialog();
        });
        console.log(successResult);
      },
      (failedResult: Error) => {
        console.log(failedResult);
      },
    );
  }

  checkBoxClick(noteBoardData: NoteBoard) {
    noteBoardData.expanded = !noteBoardData.expanded;
    this.allComplete = this.noteBoardDataArray.every(
      (noteBoardData) => noteBoardData.checkToDelete,
    );
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    this.noteBoardDataArray.forEach(
      (noteBoardData) => (noteBoardData.checkToDelete = completed),
    );
  }

  someComplete(): boolean {
    this.allComplete =
      this.noteBoardDataArray.length > 0 &&
      this.noteBoardDataArray.every(
        (noteBoardData) => noteBoardData.checkToDelete,
      );
    return (
      this.noteBoardDataArray.filter(
        (noteBoardData) => noteBoardData.checkToDelete,
      ).length > 0 && !this.allComplete
    );
  }

  isDeleteBtnDisabled() {
    return this.noteBoardDataArray.filter((d) => d.checkToDelete).length === 0;
  }
}
