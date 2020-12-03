import { element, by, ElementFinder } from 'protractor';

export class EmployeeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-employee div table .ui-button-danger'));
  title = element.all(by.css('jhi-employee div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class EmployeeUpdatePage {
  pageTitle = element(by.id('jhi-employee-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  usernameInput = element(by.id('field_username'));
  fullnameInput = element(by.id('field_fullname'));

  managerSelect = element(by.id('field_manager'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setUsernameInput(username: string): Promise<void> {
    await this.usernameInput.sendKeys(username);
  }

  async getUsernameInput(): Promise<string> {
    return await this.usernameInput.getAttribute('value');
  }

  async setFullnameInput(fullname: string): Promise<void> {
    await this.fullnameInput.sendKeys(fullname);
  }

  async getFullnameInput(): Promise<string> {
    return await this.fullnameInput.getAttribute('value');
  }

  async managerSelectLastOption(): Promise<void> {
    await this.managerSelect.click();
    await this.managerSelect.all(by.tagName('.ui-dropdown-item')).last().click();
  }

  getManagerSelect(): ElementFinder {
    return this.managerSelect;
  }

  async getManagerSelectedOption(): Promise<string> {
    return await this.managerSelect.element(by.css('.ui-dropdown-label')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class EmployeeDeleteDialog {
  private confirmButton = element(by.css('p-confirmdialog .ui-dialog-footer button:first-of-type'));

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
