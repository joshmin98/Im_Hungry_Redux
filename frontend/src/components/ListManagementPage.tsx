import * as React from 'react';
import { useState, useEffect } from 'react';
import { withRouter } from 'react-router-dom';
import {
  Card,
  CardContent,
  Button,
  Theme,
  createStyles,
  withStyles,
} from '@material-ui/core';
import Header from './sub-components/Header';
import * as axios from 'axios';

// const hostname = 'http://localhost:8338/list?listName=';

const styles = (theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
    },
    drawerHeader: {
      display: 'flex',
      alignItems: 'center',
      padding: '0 8px',
      ...theme.mixins.toolbar,
      justifyContent: 'flex-start',
    },
    main: {
      marginTop: 10,
    },
    card: {
      marginBottom: 10,
    },
    container: {
      marginLeft: '5%',
      marginRight: '5%',
    },
  });

const ListManagementPage: React.FC<any> = props => {
  useEffect(() => {
    axios.default
      .get('http://localhost:8338/restaurants', {
        withCredentials: true,
        params: {
          query: 'burger',
          limit: 5,
          radius: 8500,
        },
      })
      .then(resp => {
        console.log(resp);
        axios.default
          .get('http://localhost:8338/list', {
            withCredentials: true,
            params: {
              listName: 'Favorites',
            },
          })
          .then(resp => console.log(resp));
      });
  }, []);

  const [list, setList]: any = useState([1, 3, 5, 7, 9]);
  const { classes } = props;

  const reorder = (list: any, startIdx: number, endIdx: number) => {
    if (endIdx === -1) {
      return;
    } else if (endIdx === list.length) {
      return;
    }

    const result = Array.from(list);
    const [removed] = result.splice(startIdx, 1);
    result.splice(endIdx, 0, removed);
    console.log(startIdx, endIdx);
    setList(result);
  };

  return (
    <div className={classes.root}>
      <Header />
      <div className={classes.container}>
        <h1>{props.location.pathname.slice(7)}</h1>
        {list.map((item: any, idx: number) => (
          <Card className={classes.card} key={`item-${item}`}>
            <CardContent>
              <div id={`listPlace-${idx}`}>{item}</div>
            </CardContent>
            <Button
              id={`item-${item}-up`}
              onClick={() => reorder(list, idx, idx - 1)}
            >
              Up
            </Button>
            <Button
              id={`item-${item}-down`}
              onClick={() => reorder(list, idx, idx + 1)}
            >
              Down
            </Button>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default withStyles(styles)(withRouter(ListManagementPage));
