import React from 'react';
import { withRouter } from 'react-router-dom';
import { withStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import axios from 'axios';
import AppBar from '@material-ui/core/AppBar';
import ToolBar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Header from './sub-components/Header';
import Paper from '@material-ui/core/Paper';
import Toolbar from '@material-ui/core/Toolbar';
import {
  Table,
  TableHead,
  TableRow,
  TableCell,
  Checkbox,
  TableBody,
  Tooltip,
  IconButton,
  TableFooter,
  TablePagination,
} from '@material-ui/core';
import DeleteIcon from '@material-ui/icons/Delete';
import FirstPageIcon from '@material-ui/icons/FirstPage';
import KeyboardArrowLeft from '@material-ui/icons/KeyboardArrowLeft';
import KeyboardArrowRight from '@material-ui/icons/KeyboardArrowRight';
import LastPageIcon from '@material-ui/icons/LastPage';

const actionsStyles = theme => ({
  root: {
    flexShrink: 0,
    color: theme.palette.text.secondary,
    marginLeft: theme.spacing.unit * 2.5,
  },
});

class TablePaginationActions extends React.Component {
  handleFirstPageButtonClick = event => {
    this.props.onChangePage(event, 0);
  };

  handleBackButtonClick = event => {
    this.props.onChangePage(event, this.props.page - 1);
  };

  handleNextButtonClick = event => {
    this.props.onChangePage(event, this.props.page + 1);
  };

  handleLastPageButtonClick = event => {
    this.props.onChangePage(
      event,
      Math.max(0, Math.ceil(this.props.count / this.props.rowsPerPage) - 1),
    );
  };

  render() {
    const { classes, count, page, rowsPerPage, theme } = this.props;

    return (
      <div className={classes.root}>
        <IconButton
          onClick={this.handleFirstPageButtonClick}
          disabled={page === 0}
          aria-label="First Page"
        >
          {theme.direction === 'rtl' ? <LastPageIcon /> : <FirstPageIcon />}
        </IconButton>
        <IconButton
          onClick={this.handleBackButtonClick}
          disabled={page === 0}
          aria-label="Previous Page"
        >
          {theme.direction === 'rtl' ? (
            <KeyboardArrowRight />
          ) : (
            <KeyboardArrowLeft />
          )}
        </IconButton>
        <IconButton
          onClick={this.handleNextButtonClick}
          disabled={page >= Math.ceil(count / rowsPerPage) - 1}
          aria-label="Next Page"
        >
          {theme.direction === 'rtl' ? (
            <KeyboardArrowLeft />
          ) : (
            <KeyboardArrowRight />
          )}
        </IconButton>
        <IconButton
          onClick={this.handleLastPageButtonClick}
          disabled={page >= Math.ceil(count / rowsPerPage) - 1}
          aria-label="Last Page"
        >
          {theme.direction === 'rtl' ? <FirstPageIcon /> : <LastPageIcon />}
        </IconButton>
      </div>
    );
  }
}

TablePaginationActions.propTypes = {
  classes: PropTypes.object.isRequired,
  count: PropTypes.number.isRequired,
  onChangePage: PropTypes.func.isRequired,
  page: PropTypes.number.isRequired,
  rowsPerPage: PropTypes.number.isRequired,
  theme: PropTypes.object.isRequired,
};

const TablePaginationActionsWrapped = withStyles(actionsStyles, {
  withTheme: true,
})(TablePaginationActions);

const styles = theme => ({
  root: {
    flexGrow: 1,
    marginTop: 10,
  },
  Header: {
    backgroundColor: '#E6FAFC',
    color: '#2B3252',
    boxShadow: 'none',
  },
  fontCss: {
    fontWeight: 'bold',
  },
  title: {
    textAlign: 'center',
    marginTop: 15,
    marginBottom: 15,
    fontSize: 70,
    fontWeight: 'bold',
    color: '#2B3252',
  },
  table: {
    width: '80%',
    marginLeft: 'auto',
    marginRight: 'auto',
    marginTop: 40,
  },
  tableCheckbox: {
    width: 20,
  },
  tableName: {
    width: 300,
  },
  tableRecipe: {},
  spacer: {
    flex: '1 1 100%',
  },
});

class Grocery extends React.Component {
  state = {
    items: [],
    allChecked: false,
    showDeleted: false,
    itemsToDelete: [],
    page: 0,
    rowsPerPage: 5,
  };
  componentDidMount() {
    axios
      .get(
        'http://localhost:8338/grocery',
        {
          withCredentials: true,
        },
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
          },
        },
      )
      .then(res => {
        var tempArr = [];
        res.data.forEach((e, i) => {
          e.id = i;
          tempArr.push(e);
        });
        this.setState({
          items: tempArr.reverse(),
        });
      });
  }
  handleChangePage = (event, page) => {
    this.setState({ page });
  };

  handleChangeRowsPerPage = event => {
    this.setState({ page: 0, rowsPerPage: event.target.value });
  };

  handleDelete = async () => {
    let update = this.state.items;
    let removeSet = new Set();
    this.state.itemsToDelete.forEach(async item => {
      removeSet.add(item);
      await axios.get(
        `http://localhost:8338/grocery/delete?index=${item}`,
        {
          withCredentials: true,
        },
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
          },
        },
      );
    });

    for (let i = 0; i < update.length; i++) {
      if (removeSet.has(update[i].id)) {
        update.splice(i, 1);
      }
    }

    this.setState({
      items: update,
    });
  };

  async checkAllBoxes() {
    var tempArr = this.state.itemsToDelete;
    if (tempArr.length === 0) {
      await this.state.items.forEach((e, i) => {
        tempArr.push(i);
      });
    } else {
      if (tempArr.length === this.state.items.length) {
        tempArr = [];
      } else {
        tempArr = [];
        await this.state.items.forEach((e, i) => {
          tempArr.push(i);
        });
      }
    }
    this.setState({
      itemsToDelete: tempArr,
      allChecked: !this.state.allChecked,
      showDeleted: !this.state.showDeleted,
    });
  }
  addToDelete = async index => {
    var tempArr = this.state.itemsToDelete;
    if (tempArr === []) {
      tempArr.push(index);
    } else {
      if (tempArr.includes(index)) {
        console.log('here');
        var i = tempArr.indexOf(index);
        i > -1 && (await tempArr.splice(i, 1));
        //console.log(tempArr)
      } else {
        tempArr.push(index);
      }
    }
    this.setState({
      itemsToDelete: tempArr,
      showDeleted: tempArr !== [] ? true : false,
    });
  };
  render() {
    console.log(this.state.itemsToDelete);
    const { classes } = this.props;
    const { rows, rowsPerPage, page } = this.state;
    const emptyRows =
      rowsPerPage -
      Math.min(rowsPerPage, this.state.items.length - page * rowsPerPage);
    return (
      <div className={classes.root}>
        <Header searchPage={true} />
        <div>
          <Typography
            component="h1"
            variant="h2"
            className={classes.title}
            id="header"
          >
            Grocery List
          </Typography>
        </div>
        <div className={classes.table}>
          <Paper>
            <Toolbar>
              <div>
                {this.state.itemsToDelete.length > 0 ? (
                  <Typography variant="h6">
                    {this.state.itemsToDelete.length + ' items selected'}
                  </Typography>
                ) : (
                  <Typography variant="h6">Ingredients</Typography>
                )}
              </div>
              <div className={classes.spacer} />
              {this.state.showDeleted && (
                <Tooltip>
                  <IconButton>
                    <DeleteIcon
                      id="trash"
                      onClick={() => this.handleDelete()}
                    />
                  </IconButton>
                </Tooltip>
              )}
            </Toolbar>
            <div>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell
                      padding="checkbox"
                      className={classes.tableCheckbox}
                    >
                      <Checkbox
                        checked={
                          this.state.itemsToDelete.length ===
                          this.state.items.length
                        }
                        onChange={this.checkAllBoxes.bind(this)}
                      />
                    </TableCell>
                    <TableCell align="left" className={classes.tableName}>
                      <Typography>Name</Typography>
                    </TableCell>
                    <TableCell align="left" className={classes.tableRecipe}>
                      <Typography>Recipes</Typography>
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {this.state.items
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((e, index) => {
                      return (
                        <TableRow key={index} hover>
                          <TableCell
                            padding="checkbox"
                            className={classes.tableCheckbox}
                          >
                            <Checkbox
                              id={`checkbox-${index}`}
                              checked={this.state.itemsToDelete.includes(e.id)}
                              onChange={() => {
                                this.addToDelete(e.id);
                              }}
                            />
                          </TableCell>
                          <TableCell align="left" className={classes.tableName}>
                            <Typography>{e.name}</Typography>
                          </TableCell>
                          <TableCell
                            align="left"
                            id={`item-${index}`}
                            className={classes.tableRecipe}
                          >
                            <Typography>{e.recipe}</Typography>
                          </TableCell>
                        </TableRow>
                      );
                    })}
                </TableBody>
                <TableFooter>
                  <TableRow>
                    <TablePagination
                      rowsPerPageOptions={[5, 10, 25]}
                      colSpan={3}
                      count={this.state.items.length}
                      rowsPerPage={rowsPerPage}
                      page={page}
                      SelectProps={{
                        native: true,
                      }}
                      onChangePage={this.handleChangePage}
                      onChangeRowsPerPage={this.handleChangeRowsPerPage}
                      ActionsComponent={TablePaginationActionsWrapped}
                    />
                  </TableRow>
                </TableFooter>
              </Table>
            </div>
          </Paper>
        </div>
      </div>
    );
  }
}

Grocery.propTypes = {
  classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(withRouter(Grocery));
