import { element, by, ElementFinder } from 'protractor';

export class WithIdStringDetailsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-with-id-string-details div table .ui-button-danger'));
  title = element.all(by.css('jhi-with-id-string-details div h2#page-heading span')).first();

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

export class WithIdStringDetailsUpdatePage {
  pageTitle = element(by.id('jhi-with-id-string-details-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  withIdStringIdInput = element(by.id('field_withIdStringId'));
  nameInput = element(by.id('field_name'));

  withIdStringSelect = element(by.id('field_withIdString'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setWithIdStringIdInput(withIdStringId: string): Promise<void> {
    await this.withIdStringIdInput.sendKeys(withIdStringId);
  }

  async getWithIdStringIdInput(): Promise<string> {
    return await this.withIdStringIdInput.getAttribute('value');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async withIdStringSelectLastOption(): Promise<void> {
    await this.withIdStringSelect.click();
    await this.withIdStringSelect.all(by.tagName('.ui-dropdown-item')).last().click();
  }

  getWithIdStringSelect(): ElementFinder {
    return this.withIdStringSelect;
  }

  async getWithIdStringSelectedOption(): Promise<string> {
    return await this.withIdStringSelect.element(by.css('.ui-dropdown-label')).getText();
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

export class WithIdStringDetailsDeleteDialog {
  private confirmButton = element(by.css('p-confirmdialog .ui-dialog-footer button:first-of-type'));

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
