import * as React from 'react';
import { useState } from 'react';
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
    setList(result);
  };

  return (
    <div className={classes.root}>
      <Header />
      <div className={classes.container}>
        <h1>{props.location.pathname.slice(7)}</h1>
        {list.map((item: any, idx: number) => (
          <Card key={`item-${item}`}>
            <CardContent>
              <div id={`listPlace-${idx}`}>{item}</div>
            </CardContent>
            <Button
              id={`item-${item}-up`}
              onClick={() => reorder(list, idx, idx + 1)}
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
