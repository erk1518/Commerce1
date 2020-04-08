import React from "react";
// eslint-disable-next-line no-unused-vars
import { render } from "react-dom";

//This is not working
import { Table, Icon, Segment, Grid } from "semantic-ui-react";

class TableRenderingExample extends React.Component {
    constructor() {
        super();

        this.state = {
            data : [
                {id : 1, date : "2014-04-18", total : 121.0, status : "Shipped", name : "A", points: 5, percent : 50},
                {id : 2, date : "2014-04-21", total : 121.0, status : "Not Shipped", name : "B", points: 10, percent: 60},
                {id : 3, date : "2014-08-09", total : 121.0, status : "Not Shipped", name : "C", points: 15, percent: 70},
                {id : 4, date : "2014-04-24", total : 121.0, status : "Shipped", name : "D", points: 20, percent : 80},
                {id : 5, date : "2014-04-26", total : 121.0, status : "Shipped", name : "E", points: 25, percent : 90},
            ],
            expandedRows : []
        };
    }

    handleRowClick(rowId) {
        const currentExpandedRows = this.state.expandedRows;
        const isRowCurrentlyExpanded = currentExpandedRows.includes(rowId);

        const newExpandedRows = isRowCurrentlyExpanded ?
            currentExpandedRows.filter(id => id !== rowId) :
            currentExpandedRows.concat(rowId);

        this.setState({expandedRows : newExpandedRows});
    }

    renderItemCaret(rowId) {
        const currentExpandedRows = this.state.expandedRows;
        const isRowCurrentlyExpanded = currentExpandedRows.includes(rowId);

        if (isRowCurrentlyExpanded) {
            return <Icon name="caret down" />;
        } else {
            return <Icon name="caret right" />;
        }
    }

    renderItemDetails(item) {
        return (
            //This will be where the error details goes
            <Segment basic>
                <Grid columns={3}>
                    <Grid.Column>
                        <span>Name: {item.name}</span>
                    </Grid.Column>

                    <Grid.Column>
                        <span>Point: {item.points}</span>
                    </Grid.Column>

                    <Grid.Column>
                        <span>Percent: {item.percent}</span>
                    </Grid.Column>
                </Grid>
            </Segment>
        );
    }

    renderItem(item, index) {
        const clickCallback = () => this.handleRowClick(index);
        const itemRows = [
            <Table.Row onClick={clickCallback} key={"row-data-" + index}>
                <Table.Cell>{this.renderItemCaret(index)}</Table.Cell>
                <Table.Cell>{item.date}</Table.Cell>
                <Table.Cell>{item.total}</Table.Cell>
                <Table.Cell>{item.status}</Table.Cell>
            </Table.Row>
        ];

        if (this.state.expandedRows.includes(index)) {
            itemRows.push(
                <Table.Row key={"row-expanded-" + index}>
                    <Table.Cell colSpan="4">{this.renderItemDetails(item)}</Table.Cell>
                </Table.Row>
            );
        }

        return itemRows;
    }


    render() {
        let allItemRows = [];

        this.state.data.forEach((item, index) => {
            const perItemRows = this.renderItem(item, index);
            allItemRows = allItemRows.concat(perItemRows);
        });

        return (
            <Table selectable>
                <Table.Header>
                    <Table.Row>
                        <Table.HeaderCell />
                        <Table.HeaderCell>Date</Table.HeaderCell>
                        <Table.HeaderCell>Total</Table.HeaderCell>
                        <Table.HeaderCell>Status</Table.HeaderCell>
                    </Table.Row>
                </Table.Header>

                <Table.Body>{allItemRows}</Table.Body>
            </Table>
        );
    }
}

export default TableRenderingExample;