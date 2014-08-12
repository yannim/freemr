describe('tenant', function () {

    beforeEach(function () {
        browser().navigateTo('/index.html');

    });

    afterEach(function () {

    });

    it('List all tenants. There are 3 trs for display data.', function () {
        expect(repeater('tbody tr').count()).toBe(3);
    });

    it('Edit the first tenant. Change the name to \'ggg\', the description to \'dd\', the organizationName to \'oo\' and the contactInformation to \'cc\'."', function () {
        element("tbody>tr:eq(0) button.editTenantBtn").click();
        input('t.name').enter('ggg');
        input('t.description').enter('dd');
        input('t.organizationName').enter('oo');
        input('t.contactInformation').enter('cc');
        element("tbody>tr:eq(0) button.saveEditTenantBtn").click();
        expect(element("tbody>tr:eq(0)>td:eq(0)>a:eq(0)").text()).toBe("ggg");
    });

    it('Archive the first tenant in the table. After archived, there should only 3 trs.', function () {
        element("tbody>tr:eq(0) button.deleteTenantBtn").click();
        element("a.confirmDeleteTenantBtn").click();
        expect(repeater('tbody tr').count()).toBe(3);
    });

    it('Create a new tenant. After created the table should have 4 trs', function () {
        element('a.newTenantButton').click();
        input('t.name').enter('gg');
        input('t.description').enter('d');
        input('t.organizationName').enter('o');
        input('t.contactInformation').enter('c');
        element("tbody>tr:eq(0) button.saveEditTenantBtn").click();
        expect(repeater('tbody tr').count()).toBe(4);
    });

});