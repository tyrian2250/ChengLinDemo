import { Component, OnInit } from '@angular/core';
import { ToolService } from '../tool.service';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'note-board-list',
  templateUrl: './note-board-list.component.html',
  styleUrls: ['./note-board-list.component.css']
})
export class NoteBoardListComponent implements OnInit {

  constructor(
    private tool: ToolService,
    public dialog: MatDialog
  ) { }

  title: string = '';
  content: string = '';
  pollingFrequency: number = 300000;
  noteBoardDataArray: Array<any> = [];
  allComplete: boolean = false;
  displayedColumns: string[] = ['timestamp_without_timezone', 'title'];

  pollingData() {
    setTimeout(() => {
    }, this.pollingFrequency);
  }

  updateData(callback?: Function) {
    this.tool.doPost('getNoteBoard',
      {
        title: this.title,
        content: this.content
      },
      (successResult: any) => {
        successResult.Data.forEach((newData: any) => {
          newData.expanded = this.noteBoardDataArray.filter(currentData => currentData.id === newData.id)[0]?.expanded;
          newData.checkToDelete = this.noteBoardDataArray.filter(currentData => currentData.id === newData.id)[0]?.checkToDelete;
        });
        this.noteBoardDataArray = successResult.Data;

        callback && callback() || this.pollingData();
        console.log(successResult);
      },
      (failedResult: any) => {
        this.pollingData();
        console.log(failedResult);
      }
    );

  }
  ngOnInit(): void {
    this.updateData();
    this.pollingData();
  }

  deleteNoteBoard() {
    this.tool.doPost('deleteNoteBoard',
      {
        idArray: this.noteBoardDataArray.map(noteBoardData => noteBoardData.checkToDelete && noteBoardData.id).filter(id => !!id)
      },
      (successResult: any) => {
        this.updateData(() => {
          this.tool.openDialog();
        });
        console.log(successResult);
      },
      (failedResult: any) => {
        console.log(failedResult);
      }
    );
  }

  checkBoxClick(noteBoardData: any) {
    noteBoardData.expanded = !noteBoardData.expanded;
    this.allComplete = this.noteBoardDataArray.every(noteBoardData => noteBoardData.checkToDelete);
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    this.noteBoardDataArray.forEach(noteBoardData => (noteBoardData.checkToDelete = completed));
  }

  someComplete(): boolean {
    this.allComplete = this.noteBoardDataArray.length > 0 && this.noteBoardDataArray.every(noteBoardData => noteBoardData.checkToDelete);
    return this.noteBoardDataArray.filter(noteBoardData => noteBoardData.checkToDelete).length > 0 && !this.allComplete;
  }

  isDeleteBtnDisabled() {
    return this.noteBoardDataArray.filter(d => d.checkToDelete).length === 0;
  }

}
